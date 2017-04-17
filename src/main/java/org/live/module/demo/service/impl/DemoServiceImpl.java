package org.live.module.demo.service.impl;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.module.demo.entity.DemoVo;
import org.live.module.demo.repository.DemoRepository;
import org.live.module.demo.service.DemoService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by KAM on 2017/3/28.
 */
@Service(value = "demoService")
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoRepository demoRepository;

    @Override
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = demoRepository.count();
        Map<String, Object> params = DataTableUtils.parseMap(request);
        Page<DemoVo> page = demoRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter"));
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal);
        return model;
    }

    @Override
    public boolean del(List<String> ids) {
        if (ids.size() > 0) {
            try {
                for (String id : ids) {
                    demoRepository.delete(id);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void handleEXCELData(InputStream inputStream) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = wb.getSheetAt(0); // 工作表

        Iterator<Row> rows = sheet.iterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                String val = cell.getStringCellValue();
                System.out.print(val + "\t");
            }
            System.out.print("\n");
        }

    }
}
