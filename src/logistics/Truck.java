package logistics;

/**
 * [Concrete Product 1] theo Refactoring Guru.
 * Phương tiện vận tải đường bộ bằng Xe tải.
 */
public class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("  🚚 [Truck] Vận chuyển hàng hóa bằng đường bộ trong thùng xe có tải trọng lớn.");
    }
}

