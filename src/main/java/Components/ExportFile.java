package Components;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import MODEL.GiaoDich;
import MODEL.KhachHang;
import MODEL.SanPham;
import MODEL.Ve;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExportFile {

    public void exportTicketInvoiceToPDF(GiaoDich giaoDich, KhachHang khachHang, List<Ve> veList, 
                                        List<SanPham> sanPhamList, double tongTien, String tenPhim, 
                                        String ngayChieu, String gioChieu, String tenPhong) {
        try {
            // Đường dẫn lưu file
            String directoryPath = "D:\\Documents\\IUH\\3\\HK6\\HSKJAVA\\BaiTapLonCuoiKy\\quan-ly-rap-phim\\CinemaInvoices";
            File directory = new File(directoryPath);

            // Tạo thư mục nếu chưa tồn tại
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = directoryPath + "\\HoaDonVe_" + giaoDich.getMaGiaoDich() + ".pdf";
            File file = new File(filePath);

            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                // Load font hỗ trợ Tiếng Việt
                PdfFont font = PdfFontFactory.createFont("FONTS/Arial.ttf", PdfEncodings.IDENTITY_H);

                // Header thông tin rạp chiếu phim
                document.add(new Paragraph("RẠP CHIẾU PHIM XYZ")
                        .setFont(font)
                        .setFontSize(18)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("123 Đường ABC, Quận 1, TP. Hồ Chí Minh")
                        .setFont(font)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("Hotline: 19001234")
                        .setFont(font)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("Website: www.rapchieuphimxyz.com")
                        .setFont(font)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("\n"));

                // Tiêu đề hóa đơn
                document.add(new Paragraph("HÓA ĐƠN THANH TOÁN VÉ")
                        .setFont(font)
                        .setBold()
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("\n"));

                // Thông tin khách hàng và giao dịch
                Table infoTable = new Table(new float[]{1, 1});
                infoTable.setWidth(UnitValue.createPercentValue(100));

                infoTable.addCell(new Cell().add(new Paragraph("Tên khách hàng: " + khachHang.getTenKhachHang()).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Mã giao dịch: " + giaoDich.getMaGiaoDich()).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Số điện thoại: " + khachHang.getSoDienThoai()).setFont(font)).setBorder(Border.NO_BORDER));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                infoTable.addCell(new Cell().add(new Paragraph("Ngày lập: " + dateFormat.format(giaoDich.getThoiGianGiaoDich())).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Email: " + (khachHang.getEmail() != null ? khachHang.getEmail() : "")).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Phim: " + tenPhim).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Ngày chiếu: " + ngayChieu).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Giờ chiếu: " + gioChieu).setFont(font)).setBorder(Border.NO_BORDER));
                infoTable.addCell(new Cell().add(new Paragraph("Phòng chiếu: " + tenPhong).setFont(font)).setBorder(Border.NO_BORDER));

                document.add(infoTable);
                document.add(new Paragraph("\n"));

                // Bảng chi tiết vé
                float[] columnWidths = {1, 3, 2, 2};
                Table ticketTable = new Table(columnWidths);
                ticketTable.setWidth(UnitValue.createPercentValue(100));

                ticketTable.addHeaderCell(new Cell().add(new Paragraph("STT").setFont(font).setBold()));
                ticketTable.addHeaderCell(new Cell().add(new Paragraph("Ghế").setFont(font).setBold()));
                ticketTable.addHeaderCell(new Cell().add(new Paragraph("Loại ghế").setFont(font).setBold()));
                ticketTable.addHeaderCell(new Cell().add(new Paragraph("Giá vé").setFont(font).setBold()));

                DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
                int stt = 1;
                for (Ve ve : veList) {
                    ticketTable.addCell(new Cell().add(new Paragraph(String.valueOf(stt++)).setFont(font)));
                    ticketTable.addCell(new Cell().add(new Paragraph(ve.getGhe().getSoGhe()).setFont(font)));
                    ticketTable.addCell(new Cell().add(new Paragraph(ve.getGhe().getLoaiGhe().toString()).setFont(font)));
                    ticketTable.addCell(new Cell().add(new Paragraph(decimalFormat.format(ve.getGiaVe())).setFont(font)));
                }
                document.add(ticketTable);

                // Bảng chi tiết sản phẩm/dịch vụ (nếu có)
                if (!sanPhamList.isEmpty()) {
                    document.add(new Paragraph("\n"));
                    Table productTable = new Table(new float[]{1, 3, 2, 2});
                    productTable.setWidth(UnitValue.createPercentValue(100));

                    productTable.addHeaderCell(new Cell().add(new Paragraph("STT").setFont(font).setBold()));
                    productTable.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setFont(font).setBold()));
                    productTable.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setFont(font).setBold()));
                    productTable.addHeaderCell(new Cell().add(new Paragraph("Thành tiền").setFont(font).setBold()));

                    stt = 1;
                    for (SanPham sp : sanPhamList) {
                        productTable.addCell(new Cell().add(new Paragraph(String.valueOf(stt++)).setFont(font)));
                        productTable.addCell(new Cell().add(new Paragraph(sp.getTenSanPham()).setFont(font)));
                        productTable.addCell(new Cell().add(new Paragraph("1").setFont(font)));
                        productTable.addCell(new Cell().add(new Paragraph(decimalFormat.format(sp.getGiaTien())).setFont(font)));
                    }
                    document.add(productTable);
                }

                // Tổng tiền
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Tổng tiền: " + decimalFormat.format(tongTien))
                        .setFont(font)
                        .setBold()
                        .setFontSize(16)
                        .setTextAlignment(TextAlignment.RIGHT));

                // Lời cảm ơn
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("CẢM ƠN QUÝ KHÁCH!")
                        .setFont(font)
                        .setFontSize(20)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER));

                JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công tại: " + filePath);

                // Mở file PDF sau khi xuất
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}