package com.example.demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class DemoUtilTest {

    /**
     * DemoUtilクラスのcheckDateメソッドの確認
     */
    @Test
    public void testCheckDate(){
        //第一引数yearがnullまたは空文字の場合に、1が返ってくることを確認
        //assertEquals(期待値, 実際の値)は、期待値=実際の値であればOKと判断する
        int return1 = DemoUtil.checkDate(null, "12", "14");
        assertEquals(1, return1);

        //第二引数monthがnullまたは空文字の場合に、2が返ってくることを確認
        int return2 = DemoUtil.checkDate("2019", "", "14");
        assertEquals(2, return2);

        //第三引数dayがnullまたは空文字の場合に、3が返ってくることを確認
        int return3 = DemoUtil.checkDate("2019", "12", null);
        assertEquals(3, return3);

        //引数のyear,month,dayがあり得ない日付の場合に、4が返ってくることを確認
        int return4 = DemoUtil.checkDate("2019", "2", "29");
        assertEquals(4, return4);

        //引数のyear,month,dayが正しい日付の場合に、5が返ってくることを確認
        int return5 = DemoUtil.checkDate("2019", "12", "14");
        assertEquals(0, return5);
    }

    @Test
    public void testAddZero(){
        //引数がnullの場合に、nullが返ってくることを確認
        //assertNull(実際の値)は、実際の値=nullであればOKと判断する
        String return1 = DemoUtil.addZero(null);
        assertNull(return1);

        //引数が空文字の場合に、空文字が返ってくることを確認
        String return2 = DemoUtil.addZero("");
        assertEquals("", return2);

        //引数が1桁の文字列の場合に、先頭に0が付与されることを確認
        String return3 = DemoUtil.addZero("1");
        assertEquals("01", return3);

        //引数が2桁以上の文字列の場合に、引数の値が返ってくることを確認
        String return4 = DemoUtil.addZero("11");
        assertEquals("11", return4);
    }

    @Test
    public void testIsEmpty(){
        //引数がnullの場合に、trueが返ってくることを確認
        //assertTrue(実際の値)は、実際の値=trueであればOKと判断する
        boolean return1 = DemoUtil.isEmpty(null);
        assertTrue(return1);

        //引数が空文字の場合に、trueが返ってくることを確認
        boolean return2 = DemoUtil.isEmpty("");
        assertTrue(return2);

        //引数がnull・空文字以外の場合に、falseが返ってくることを確認
        //assertFalse(実際の値)は、実際の値=falseであればOKと判断する
        boolean return3 = DemoUtil.isEmpty("abcde");
        assertFalse(return3);
    }
}
