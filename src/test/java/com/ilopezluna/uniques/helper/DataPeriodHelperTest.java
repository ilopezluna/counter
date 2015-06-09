package com.ilopezluna.uniques.helper;

import com.ilopezluna.uniques.domain.DataPeriod;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by ignasi on 9/6/15.
 */
public class DataPeriodHelperTest {

    @Test
    public void testMerge() throws Exception {

        DataPeriod base = new DataPeriod(LocalDate.now().minusDays(1), LocalDate.now());
        DataPeriod against = new DataPeriod(LocalDate.now().minusDays(1), LocalDate.now());

        DataPeriod and = DataPeriodHelper.merge(base, against, Operation.AND);
        DataPeriod or = DataPeriodHelper.merge(base, against, Operation.OR);
        Assert.assertEquals(0, and.count());
        Assert.assertEquals(0, or.count());

        DataPoint dataPoint = new DataPoint(Key.DEFAULT, LocalDate.now());
        dataPoint.hit(1);
        base.addDataPoint(dataPoint);

        and = DataPeriodHelper.merge(base, against, Operation.AND);
        or = DataPeriodHelper.merge(base, against, Operation.OR);
        Assert.assertEquals(0, and.count());
        Assert.assertEquals(1, or.count());

        against.addDataPoint(dataPoint);
        and = DataPeriodHelper.merge(base, against, Operation.AND);
        or = DataPeriodHelper.merge(base, against, Operation.OR);
        Assert.assertEquals(1, and.count());
        Assert.assertEquals(1, or.count());
    }
}