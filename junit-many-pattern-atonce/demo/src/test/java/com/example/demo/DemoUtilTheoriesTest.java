package com.example.demo;

import org.junit.Ignore;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class DemoUtilTheoriesTest {

    /**
     * テストデータ
     * DemoUtil.checkDateに渡す引数、想定結果を順に配列にしている
     * DateFixtureクラスは下方に定義
     */
    @DataPoints
    public static DateFixture[] testFixtures = {
            new DateFixture(null, "12", "14", 1)
            ,new DateFixture("2019", "", "14", 2)
            ,new DateFixture("2019", "12", null, 3)
            ,new DateFixture("2019", "2", "29", 4)
            ,new DateFixture("2019", "12", "14", 0)
    };

    /**
     * テストメソッド
     * @param dateFixture DateFixtureクラスのオブジェクト
     */
    @Theory
    public void testCheckDate(DateFixture dateFixture){
        //引数に渡された値を表示
        //「@DataPoints」アノテーションを付与したtestFixturesの配列の要素が
        //順に設定されることが確認できる
        System.out.println(dateFixture.year + "年" + dateFixture.month
                + "月" + dateFixture.day + "日"
                + " : (想定結果) " + dateFixture.expect);
        //結果を判定
        assertEquals(dateFixture.expect
                , DemoUtil.checkDate(dateFixture.year, dateFixture.month, dateFixture.day));
    }

    /**
     * DemoUtil.checkDateに渡す引数、想定結果を格納するための内部クラス
     * テスト対象から除外するため、@Ignoreを付与している
     */
    @Ignore("its fixture class")
    public static class DateFixture {
        private String year;   //年
        private String month;  //月
        private String day;    //日
        private int expect;    //想定結果

        public DateFixture(String year, String month, String day, int expect) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.expect = expect;
        }
    }
}
