package alexander.marashov.smo.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.actor_classes.ALabel;
import alexander.marashov.smo.actor_classes.AlignedGroup;
import alexander.marashov.smo.components.Simulator;

public class Results extends AlignedGroup {

    private final int rowsCount;
    private final int colsCount;

    public Results(float width, float height, Simulator.SimulationResult result) {

        super(
                width, height,
                7 + result.generatedCount.length + result.consumersBusyCoefficients.length,
                8
        );

        colsCount = 8;
        rowsCount = 7 + result.generatedCount.length + result.consumersBusyCoefficients.length;
        final int last = colsCount - 1;
        final int suppliersCount = result.generatedCount.length;
        final int consumersCount = result.consumersBusyCoefficients.length;

        addLabel("Общая информация", 0, 0, 0, last);
        addLabel("Количество итераций", 1, 0, 1, colsCount / 2);
        addLabel("" + result.iterationsCount, 1, colsCount / 2, 1, colsCount / 2);
        addLabel("Вероятность отказа", 2, 0, 2, colsCount / 2);
        addLabel("" + result.rejectProbability, 2, colsCount / 2, 2, colsCount / 2);

        addLabel("Характеристики источников ВС", 3, 0, 3, last);
        addLabel("Источник", 4, 0, 4, 0);
        addLabel("Кол-во заявок", 4, 1, 4, 1);
        addLabel("P отказа", 4, 2, 4, 2);
        addLabel("T пребывания", 4, 3, 4, 3);
        addLabel("T в буфере", 4, 4, 4, 4);
        addLabel("T в приборе", 4, 5, 4, 5);
        addLabel("Д в буфере", 4, 6, 4, 6);
        addLabel("Д в приборе", 4, 7, 4, 7);

        for (int i = 5; i < 5 + suppliersCount; ++i) {
            final int index = i - 5;
            addLabel("" + index, i, 0, i, 0);
            addLabel("" + result.generatedCount[index], i, 1, i, 1);
            addLabel(String.format("%f", result.supplierRejectProbability[index]), i, 2, i, 2);
            addLabel(String.format("%f", result.averageInSystemTime[index]), i, 3, i, 3);
            addLabel(String.format("%f", result.averageInBufferTime[index]), i, 4, i, 4);
            addLabel(String.format("%f", result.averageInConsumerTime[index]), i, 5, i, 5);
            addLabel(String.format("%f", result.varianceBufferTime[index]), i, 6, i, 6);
            addLabel(String.format("%f", result.varianceConsumerTime[index]), i, 7, i, 7);
        }

        final int offset = 5 + suppliersCount;
        addLabel("Характеристики приборов ВС", offset, 0, offset, last);
        addLabel("Номер прибора", offset + 1, 0, offset + 1, colsCount / 2);
        addLabel("Коэффициент использования", offset + 1, colsCount / 2, offset + 1, last);
        for (int i = offset + 2; i < offset + consumersCount + 2; ++i) {
            final int index = i - offset - 2;
            addLabel("" + index, i, 0, i, colsCount / 2);
            addLabel(String.format("%f", result.consumersBusyCoefficients[index]), i, colsCount / 2, i, last);
        }


    }

    private int row(int i) {
        return rowsCount - 1 - i;
    }

    private void addLabel(String text, int r1, int c1, int r2, int c2) {
        setActor(new ALabel(text, text, 30, Color.WHITE, Align.center, 5f, Color.BLACK),
                row(r1), c1, row(r2), c2);
    }
}
