package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateCheckUtil.class})
public class DateCheckUtilTest {

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void init(){
        //DateCheckUtilクラスをSpyで一部をMock設定
        PowerMockito.spy(DateCheckUtil.class);
    }

    /**
     * DateCheckUtilクラスのcheckDateメソッドの確認
     * (DateCheckUtil.isCorrectDateをMock設定した場合の確認)
     */
    @Test
    public void testDateCheckUtilCheckDateMock() throws Exception{
        //privateメソッドであるDateCheckUtil.isCorrectDateを呼び出したときに、
        //falseを返却するようMock設定
        PowerMockito.doReturn(false)
                .when(DateCheckUtil.class, "isCorrectDate"
                        , Mockito.any(), Mockito.any());

        //DateCheckUtil.checkDateを呼び出し、戻り値を確認
        //checkDateメソッドの引数に正しい年月日を指定しているが、
        //isCorrectDateメソッドでfalseを返すようにMock設定したため、
        //DateCheckUtil.checkDateメソッドの戻り値が4になる
        int retVal = DateCheckUtil.checkDate("2012", "12", "27");
        assertEquals(4, retVal);
    }

    /**
     * DateCheckUtilクラスのcheckDateメソッドの確認
     * (DateCheckUtil.isCorrectDateの呼出回数と引数の確認)
     */
    @Test
    public void testDateCheckUtilCheckDateVerify() throws Exception{
        //DateCheckUtil.checkDateを呼び出し、戻り値を確認
        int retVal = DateCheckUtil.checkDate("2012", "12", "32");
        assertEquals(4, retVal);

        //DateCheckUtil.isCorrectDateメソッドの呼出回数が1回であることを確認し、
        //その際の引数も取得
        ArgumentCaptor<String> dateStrArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dateFormatArg = ArgumentCaptor.forClass(String.class);
        PowerMockito.verifyPrivate(DateCheckUtil.class, Mockito.times(1))
                .invoke("isCorrectDate", dateStrArg.capture(), dateFormatArg.capture());

        //DateCheckUtil.isCorrectDateメソッドの引数を確認
        assertEquals("20121232", dateStrArg.getValue());
        assertEquals("uuuuMMdd", dateFormatArg.getValue());
    }

}
