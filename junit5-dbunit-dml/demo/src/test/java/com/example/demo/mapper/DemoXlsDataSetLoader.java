package com.example.demo.mapper;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class DemoXlsDataSetLoader extends AbstractDataSetLoader {

    /**
     * ExcelのデータをDBに格納する際の変換仕様を定義する.
     * @param resource リソースオブジェクト
     * @return 変換後データセット
     * @throws IOException      入出力例外
     * @throws DataSetException データセット例外
     */
    @Override
    protected IDataSet createDataSet(Resource resource) throws IOException, DataSetException {
        try (InputStream inputStream = resource.getInputStream()) {
            XlsDataSet xlsDataSet = new XlsDataSet(inputStream);
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(xlsDataSet);
            // Excel上で「(NULL)」という記載は、DB上ではNULL値に変換する
            replacementDataSet.addReplacementObject("(NULL)", null);
            return replacementDataSet;
        }
    }

}
