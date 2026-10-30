package logistics;

/**
 * [Concrete Product Mở rộng] Vận tải hàng không bằng Máy bay.
 * Minh chứng cho nguyên lý OCP: Khi công ty mở rộng thêm đường bay,
 * chỉ cần tạo mới class này mà không cần sửa Truck hay Ship.
 */
public class Plane implements Transport {
    @Override
    public String deliver() {
        String msg = "✈️ <b>[Plane]:</b> Đã cất cánh! Hàng được giao hỏa tốc xuyên quốc gia qua đường hàng không.";
        System.out.println("  ✈️ [Plane] Vận chuyển hàng hóa hỏa tốc xuyên quốc gia bằng đường hàng không.");
        return msg;
    }
}

