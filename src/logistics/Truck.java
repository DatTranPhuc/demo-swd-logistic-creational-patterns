package logistics;

/**
 * [Concrete Product 1] theo Refactoring Guru.
 * Phương tiện vận tải đường bộ bằng Xe tải.
 */
public class Truck implements Transport {
    @Override
    public String deliver() {
        String msg = "🚚 <b>[Truck]:</b> Đã xuất kho! Hàng được vận chuyển bằng đường bộ trong thùng xe tải trọng tải lớn.";
        System.out.println("  🚚 [Truck] Vận chuyển hàng hóa bằng đường bộ trong thùng xe có tải trọng lớn.");
        return msg;
    }
}

