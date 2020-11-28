package alexander.marashov.smo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;

import alexander.marashov.smo.components.Simulator;

public class Script implements Iterator<Simulator>, Closeable {

    private int counter = 0;

    private int suppliersCount = 1;
    private int consumersCount = 1;
    private int bufferSize = 1;

    private double[] suppliersIntensity = { 1. };
    private double[] consumersIntensity = { 1. };

    private int consumersFrom = 1;
    private int consumersTimes = 0;
    private int initConsumersTimes = 0;
    private int consumersBy = 1;

    private double consumersIntensityFrom = 1.;
    private int[] consumersIntensityTimes = {0};
    private int initConsumersIntensityTimes = 0;
    private double consumersIntensityBy = 1;

    private int bufferFrom = 1;
    private int bufferTimes = 0;
    private int initBufferTimes = 0;
    private int bufferBy = 1;

    private final Writer writer;

    public Script() {
        writer = Gdx.files.local("output.txt").writer(false);
    }

    public String parse(final FileHandle fileHandle) {
        final String[] commands = fileHandle.readString().split("\n");
        try {
            for (final String command : commands) {
                final String[] args = command.split(" ");
                if (args.length == 0) {
                    continue;
                }
                switch (args[0]) {
                    case "suppliers":
                        if (args[1].equals("set")) {
                            suppliersCount = Integer.parseInt(args[2]);
                            System.out.println("Suppliers count = " + suppliersCount);
                        } else {
                            throw new Exception("'suppliers' command supports only 'set' instruction");
                        }
                        break;
                    case "suppliers_intensity":
                        if (args[1].equals("set")) {
                            final String[] intensities = args[2].split("_");
                            suppliersIntensity = new double[intensities.length];
                            for (int i = 0; i < intensities.length; i++) {
                                suppliersIntensity[i] = Double.parseDouble(intensities[i]);
                            }
                            System.out.println("Suppliers intensity = " + Arrays.toString(suppliersIntensity));
                        } else {
                            throw new Exception("'suppliers_intensity' command supports only 'set' instruction");
                        }
                        break;
                    case "consumers":
                        if (args[1].equals("from") && args[3].equals("times") && args[5].equals("by")) {
                            consumersFrom = Integer.parseInt(args[2]);
                            consumersTimes = Integer.parseInt(args[4]);
                            initConsumersTimes = consumersTimes;
                            consumersBy = Integer.parseInt(args[6]);
                            System.out.println("Comsumers: from / times / by = " + consumersFrom + " / " + consumersTimes + " / " + consumersBy);
                        } else {
                            throw new Exception("'consumers' command supports only 'from _ times _ by _' instruction");
                        }
                        break;
                    case "consumers_intensity":
                        if (args[1].equals("from") && args[3].equals("times") && args[5].equals("by")) {
                            consumersIntensityFrom = Math.max(0.1, Double.parseDouble(args[2]));
                            initConsumersIntensityTimes = Integer.parseInt(args[4]);
                            consumersIntensityBy = Math.max(0.1, Double.parseDouble(args[6]));
                            System.out.println("consumers_intensity: from / times / by = " + consumersIntensityFrom + " / " + initConsumersIntensityTimes + " / " + consumersIntensityBy);
                        } else {
                            throw new Exception("'consumers_intensity' command supports only 'from _ time _ by _' instruction");
                        }
                        break;
                    case "buffer":
                        if (args[1].equals("from") && args[3].equals("times") && args[5].equals("by")) {
                            bufferFrom = Integer.parseInt(args[2]);
                            bufferTimes = Integer.parseInt(args[4]);
                            initBufferTimes = bufferTimes;
                            bufferBy = Integer.parseInt(args[6]);
                            System.out.println("buffer: from / time / by = " + bufferFrom + " / " + bufferTimes + " / " + bufferBy);
                        } else {
                            throw new Exception("'buffer' command supports only 'from _ time _ by _' instruction");
                        }
                        break;
                    default:
                        throw new Exception("Unknown command: " + command);
                }
            }
            consumersCount = consumersFrom;
            consumersIntensityTimes = new int[consumersCount];
            consumersIntensity = new double[consumersCount];
            Arrays.fill(consumersIntensityTimes, initConsumersIntensityTimes);
            Arrays.fill(consumersIntensity, consumersIntensityFrom);
            bufferSize = bufferFrom;

        } catch (final Exception exception) {
            return "Ошибка чтения скрипта: " + exception.getMessage();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < consumersFrom + consumersBy * consumersTimes; ++i) {
            builder.append("П").append(i+1).append("\t");
        }
        String header = "№ испытания\tКоличество приборов\t" + builder.toString() + "Размер буфера\tСредняя вер-ть отказа\tСреднее T в системе\tСредний коэф. исп.\n";
        try {
            writer.write(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return canIncreaseBuffer() || canIncreaseConsumers() || canIncreaseConsumersIntensity();
    }

    @Override
    public Simulator next() {
//        final Simulator simulator = new Simulator();
        ++counter;
        System.out.println(counter + " SIMULATOR: buffer=" + bufferSize + ", cons_intensity=" + Arrays.toString(consumersIntensity) +", consumers=" + consumersCount);
        Simulator simulator = new Simulator(
                bufferSize,
                consumersIntensity,
                suppliersIntensity
        );

        if (canIncreaseBuffer()) {
            // increase
            bufferSize += bufferBy;
            bufferTimes--;
        } else if (canIncreaseConsumersIntensity()) {
            // reset
            bufferTimes = initBufferTimes;
            bufferSize = bufferFrom;
            // increase
            for (int i = 0; i < consumersIntensityTimes.length; i++) {
                if (consumersIntensityTimes[i] > 0) {
                    consumersIntensityTimes[i]--;
                    consumersIntensity[i] += consumersIntensityBy;
                    break;
                } else if (i+1 < consumersIntensityTimes.length && consumersIntensityTimes[i+1] > 0) {
                    for (int j = i; j >= 0; j--) {
                        consumersIntensityTimes[j] = initConsumersIntensityTimes;
                        consumersIntensity[j] = consumersIntensityFrom;
                    }
                    consumersIntensity[i+1] += consumersIntensityBy;
                    consumersIntensityTimes[i+1]--;
                    break;
                }
            }
        } else if (canIncreaseConsumers()) {
            // increase
            consumersCount++;
            consumersTimes--;
            // reset
            bufferTimes = initBufferTimes;
            bufferSize = bufferFrom;
            consumersIntensity = new double[consumersCount];
            consumersIntensityTimes = new int[consumersCount];
            Arrays.fill(consumersIntensityTimes, initConsumersIntensityTimes);
            Arrays.fill(consumersIntensity, consumersIntensityFrom);

        }

        return simulator;
    }

    private boolean canIncreaseBuffer() {
        return bufferTimes > 0;
    }

    private boolean canIncreaseConsumers() {
        return consumersTimes > 0;
    }

    private boolean canIncreaseConsumersIntensity() {
        boolean hasElementToImprove = false;
        for (int intensityTime : consumersIntensityTimes) {
            hasElementToImprove = intensityTime > 0;
            if (hasElementToImprove) {
                break;
            }
        }
        return hasElementToImprove;
    }

    public void write(Simulator.SimulationResult result) {

        StringBuilder builder = new StringBuilder();
        builder.append(counter).append("\t");
        builder.append(consumersCount).append("\t");
        for (int i = 0; i < initConsumersTimes + consumersFrom; ++i) {
            if (i >= consumersIntensity.length) {
                builder.append(String.format("%.2f", 0f)).append("\t");
            } else {
                builder.append(String.format("%.2f", consumersIntensity[i])).append("\t");
            }
        }
        builder.append(bufferSize).append("\t");
        builder.append(String.format("%.6f", result.rejectProbability)).append("\t");

        double averageInSystemTime = 0;
        for (int i = 0; i < result.averageInSystemTime.length; i++) {
            averageInSystemTime += result.averageInSystemTime[i];
        }
        averageInSystemTime /= result.averageInSystemTime.length;
        builder.append(String.format("%.6f", averageInSystemTime)).append("\t");

        double consumersBusyCoefficients = 0;
        for (int i = 0; i < result.consumersBusyCoefficients.length; i++) {
            consumersBusyCoefficients += result.consumersBusyCoefficients[i];
        }
        consumersBusyCoefficients /= result.consumersBusyCoefficients.length;
        builder.append(String.format("%.6f", consumersBusyCoefficients)).append("\t");

        builder.append("\n");
        try {
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
