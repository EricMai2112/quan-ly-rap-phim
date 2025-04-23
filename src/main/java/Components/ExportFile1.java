package Components;

import java.io.File;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.IOException;

public class ExportFile1 {

    public void exportToPDF(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            String filePath = selectedDirectory.getAbsolutePath() + "/DanhSachHoaDon.pdf";

            try {
                PdfWriter writer = new PdfWriter(filePath);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                PdfFont arialFont = PdfFontFactory.createFont("FONTS/Arial.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

                // Tiêu đề
                Paragraph title = new Paragraph("DANH SÁCH HÓA ĐƠN")
                        .setFont(arialFont)
                        .setBold()
                        .setFontSize(25)
                        .setTextAlignment(TextAlignment.CENTER);
                document.add(title);
                document.add(new Paragraph("\n"));

                // Tạo bảng
                TableModel model = table.getModel();
                Table pdfTable = new Table(UnitValue.createPercentArray(new float[]{2, 2, 4, 2, 2}));
                pdfTable.setWidth(UnitValue.createPercentValue(100));

                // Thêm tiêu đề cột theo thứ tự yêu cầu
                String[] headers = {"Mã giao dịch", "Tổng tiền", "Thời gian giao dịch", "Nhân viên", "Khách hàng"};
                for (String header : headers) {
                    pdfTable.addHeaderCell(new Cell().add(new Paragraph(header).setFont(arialFont).setBold().setFontSize(12)));
                }

                // Thêm dữ liệu
                int rowCount = model.getRowCount();
                for (int row = 0; row < rowCount; row++) {
                    for (int col = 0; col < 5; col++) { // chỉ lấy 5 cột đầu tiên như mẫu
                        Object value = model.getValueAt(row, col);
                        pdfTable.addCell(new Cell().add(new Paragraph(value != null ? value.toString() : "").setFont(arialFont).setFontSize(12)));
                    }
                }

                document.add(pdfTable);
                document.close();

                JOptionPane.showMessageDialog(null, "File PDF đã được xuất thành công!");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file PDF: " + e.getMessage());
            }
        }
    }
}
