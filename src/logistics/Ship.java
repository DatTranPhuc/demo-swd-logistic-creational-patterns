package logistics;

/**
 * [Concrete Product 2] theo Refactoring Guru.
 * Phương tiện vận tải đường biển bằng Tàu chở container.
 */
public class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("  🚢 [Ship] Vận chuyển hàng hóa bằng đường biển trong các container tiêu chuẩn quốc tế.");
    }
}

