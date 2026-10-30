package logistics;

/**
 * [Creator] Lớp trừu tượng Logistics theo chuẩn Refactoring Guru.
 * 
 * Đặc điểm kiến trúc:
 * 1. Khai báo Factory Method: `createTransport()`.
 * 2. Không phụ thuộc vào lớp con (Truck/Ship) mà chỉ làm việc với Interface `Transport`.
 * 3. Chứa logic nghiệp vụ cốt lõi: `planDelivery()`.
 */
public abstract class Logistics {

    /**
     * ĐÂY CHÍNH LÀ FACTORY METHOD
     * Lớp cha nhường quyền quyết định tạo loại phương tiện nào cho các lớp con kế thừa.
     */
    public abstract Transport createTransport();

    /**
     * Logic nghiệp vụ cốt lõi: Hoàn toàn không "new" cụ thể Truck hay Ship ở đây.
     */
    public void planDelivery(String shipmentId) {
        System.out.println("------------------------------------------------------------------");
        System.out.printf("[LOGISTICS HUB] Bắt đầu lên kế hoạch vận chuyển kiện hàng: #%s\n", shipmentId);

        // Gọi Factory Method để tạo đối tượng Transport phù hợp
        Transport transport = createTransport();

        // Thực hiện giao hàng thông qua Interface chung
        transport.deliver();

        System.out.printf("[LOGISTICS HUB] Kiện hàng #%s đã được xử lý và xuất kho thành công!\n", shipmentId);
        System.out.println("------------------------------------------------------------------");
    }
}

