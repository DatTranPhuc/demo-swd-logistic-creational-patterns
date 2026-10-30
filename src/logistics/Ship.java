package logistics;

/**
 * [Concrete Product 2] theo Refactoring Guru.
 * Phương tiện vận tải đường biển bằng Tàu chở container.
 */
public class Ship implements Transport {
    @Override
    public String deliver() {
        String msg = "🚢 <b>[Ship]:</b> Đã xếp container! Hàng được chuyển qua đường biển quốc tế.";
        System.out.println("  🚢 [Ship] Vận chuyển hàng hóa bằng đường biển trong các container tiêu chuẩn quốc tế.");
        return msg;
    }
}

