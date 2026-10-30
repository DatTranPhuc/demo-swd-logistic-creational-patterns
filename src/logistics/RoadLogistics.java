package logistics;

/**
 * [Concrete Creator 1] theo Refactoring Guru.
 * Quản lý vận tải đường bộ -> Khởi tạo Truck.
 */
public class RoadLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Truck();
    }
}

