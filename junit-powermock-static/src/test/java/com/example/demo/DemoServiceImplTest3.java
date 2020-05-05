package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.Map;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;

//staticメソッドをMock化するにはPowerMockを利用
//@PrepareForTestアノテーションで、staticメソッドを含むクラスを指定
@RunWith(PowerMockRunner.class)
@PrepareForTest({DateCheckUtil.class})
public class DemoServiceImplTest3 {

    /**
     * テスト対象のクラス
     * (今回はSpring Bootを利用しないため、Serviceではなく
     * ServiceImplを対象クラスに指定している)
     */
    @InjectMocks
    private DemoServiceImpl demoServiceImpl;

    private DemoForm demoForm;

    private BindingResult bindingResult;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void init() {
        //@Mockアノテーションのモックオブジェクトを初期化
        //これを実行しないと@Mockアノテーション、@InjectMocksを付与した
        //Mockオブジェクトが利用できない
        MockitoAnnotations.initMocks(this);

        //DateCheckUtilクラスをMock化
        PowerMockito.mockStatic(DateCheckUtil.class);

        //テスト対象メソッドの引数を設定
        demoForm = makeDemoForm(Long.valueOf(1), "テスト　プリン"
                , LocalDate.of(2012, 1, 15), SexEnum.MAN);
        bindingResult = BindingResultModel.getBindingResult();
    }

    /**
     * DemoServiceImplクラスのcheckFormメソッド(DateCheckUtil.checkDateが正常時)の確認
     */
    @Test
    public void testCheckFormNormal() {
        //DateCheckUtilメソッドをMock化し、0が返却されるように設定
        PowerMockito.when(DateCheckUtil.checkDate(
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(0);

        //テスト対象メソッドの実行
        String returnVal = demoServiceImpl.checkForm(demoForm, bindingResult);

        //テスト対象メソッドを実行した結果、戻り値がconfirmであることを確認
        assertEquals("confirm", returnVal);

        //テスト対象メソッドを実行した結果、bindingResultに
        //エラーメッセージが設定されないことを確認
        Map<String, Object> mapObj = bindingResult.getModel();
        assertEquals(0, mapObj.size());
    }

    /**
     * DemoServiceImplクラスのcheckFormメソッド(DateCheckUtil.checkDateが異常時)の確認
     */
    @Test
    public void testCheckFormalDateError() {
        //DateCheckUtilメソッドをMock化し、4が返却されるように設定
        PowerMockito.when(DateCheckUtil.checkDate(
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(4);

        //テスト対象メソッドの実行
        String returnVal = demoServiceImpl.checkForm(demoForm, bindingResult);

        //テスト対象メソッドを実行した結果、戻り値がinputであることを確認
        assertEquals("input", returnVal);

        //テスト対象メソッドを実行した結果、resultに生年月日の日付が
        //不正な場合のエラーメッセージが設定されることを確認
        Map<String, Object> mapObj = bindingResult.getModel();
        BindingResultRejectValueModel resultModelYear
                = (BindingResultRejectValueModel) mapObj.get("birthYear");
        assertEquals("validation.date-invalidate", resultModelYear.getErrorCode());
        BindingResultRejectValueModel resultModelMonth
                = (BindingResultRejectValueModel) mapObj.get("birthMonth");
        assertEquals("validation.empty-msg", resultModelMonth.getErrorCode());
        BindingResultRejectValueModel resultModelDay
                = (BindingResultRejectValueModel) mapObj.get("birthDay");
        assertEquals("validation.empty-msg", resultModelDay.getErrorCode());
    }

    @Test
    public void testCheckFormDateCheckUtilArgs() {
        //DateCheckUtilメソッドをMock化し、0が返却されるように設定
        PowerMockito.when(DateCheckUtil.checkDate(
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(0);

        //テスト対象メソッドの実行
        String returnVal = demoServiceImpl.checkForm(demoForm, bindingResult);

        //DateCheckUtilが呼ばれたときの引数を取得するための設定
        ArgumentCaptor<String> strYearArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> strMonthArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> strDayArg = ArgumentCaptor.forClass(String.class);

        //DateCheckUtilが1回呼ばれたことを確認
        PowerMockito.verifyStatic(DateCheckUtil.class, Mockito.times(1));
        DateCheckUtil.checkDate(
                strYearArg.capture(), strMonthArg.capture(), strDayArg.capture());

        //DateCheckUtilの引数が2012年1月15日の年月日であることを確認
        assertEquals("2012", strYearArg.getValue());
        assertEquals("1", strMonthArg.getValue());
        assertEquals("15", strDayArg.getValue());

        //テスト対象メソッドを実行した結果、戻り値がconfirmであることを確認
        assertEquals("confirm", returnVal);

        //テスト対象メソッドを実行した結果、bindingResultに
        //エラーメッセージが設定されないことを確認
        Map<String, Object> mapObj = bindingResult.getModel();
        assertEquals(0, mapObj.size());
    }

    /**
     * Demoフォームオブジェクトを生成する
     *
     * @param id       ID
     * @param name     名前
     * @param birthDay 生年月日
     * @param sexEnum  性別Enum
     * @return Demoフォームオブジェクト
     */
    private DemoForm makeDemoForm(Long id, String name, LocalDate birthDay, SexEnum sexEnum) {
        DemoForm demoForm = new DemoForm();
        if (id != null) {
            demoForm.setId(String.valueOf(id));
        }
        demoForm.setName(name);
        if (birthDay != null) {
            demoForm.setBirthYear(String.valueOf(birthDay.getYear()));
            demoForm.setBirthMonth(String.valueOf(birthDay.getMonthValue()));
            demoForm.setBirthDay(String.valueOf(birthDay.getDayOfMonth()));
        }
        if (sexEnum != null) {
            demoForm.setSex(sexEnum.getSex());
            demoForm.setSex_value(sexEnum.getSex_value());
        }
        return demoForm;
    }

}
