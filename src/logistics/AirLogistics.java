package logistics;

/**
 * [Concrete Creator 3] Mở rộng thêm cho hệ thống.
 * Quản lý vận tải đường hàng không -> Khởi tạo Plane.
 */
public class AirLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Plane();
    }
}

