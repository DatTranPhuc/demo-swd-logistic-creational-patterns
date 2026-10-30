package logistics;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Ứng dụng Web Local trực quan (Zero-dependency - Tích hợp sẵn trong Java).
 * Chạy trên http://localhost:8080 - Mở được trên mọi trình duyệt (Chrome, Edge).
 * Thích hợp chiếu trên màn hình lớn khi thuyết trình!
 */
public class WebDemo {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // 1. Endpoint phục vụ giao diện Web HTML
        server.createContext("/", new WebUIHandler());

        // 2. Endpoint API xử lý Factory Method (/api/dispatch)
        server.createContext("/api/dispatch", new DispatchApiHandler());

        server.setExecutor(null); // Sử dụng default executor
        server.start();

        String url = "http://localhost:" + PORT;
        System.out.println("==================================================================");
        System.out.println("   🚀 LOCAL WEB SERVER ĐANG CHẠY TẠI: " + url);
        System.out.println("   Đang tự động mở trình duyệt web của bạn...");
        System.out.println("   (Nếu trình duyệt không tự mở, hãy click vào link: " + url + " )");
        System.out.println("==================================================================");

        // Tự động mở trình duyệt mặc định của máy tính
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            System.out.println("Vui lòng mở trình duyệt và truy cập: " + url);
        }
    }

    /**
     * Handler trả về toàn bộ trang Web giao diện hiện đại (HTML/CSS/JS)
     */
    static class WebUIHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = """
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demo Factory Method Pattern - Logistics Hub</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=JetBrains+Mono:wght@400;600&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --primary-dark: #1d4ed8;
            --bg: #0f172a;
            --card: #1e293b;
            --card-border: #334155;
            --text-main: #f8fafc;
            --text-muted: #94a3b8;
            --accent-truck: #38bdf8;
            --accent-ship: #34d399;
            --accent-plane: #f472b6;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
            background: var(--bg);
            color: var(--text-main);
            min-height: 100vh;
            padding: 30px 20px;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        header {
            text-align: center;
            margin-bottom: 30px;
        }
        .badge {
            display: inline-block;
            background: rgba(37, 99, 235, 0.2);
            color: #60a5fa;
            border: 1px solid rgba(96, 165, 250, 0.3);
            padding: 4px 14px;
            border-radius: 999px;
            font-size: 13px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 12px;
        }
        h1 {
            font-size: 32px;
            font-weight: 800;
            margin-bottom: 8px;
            background: linear-gradient(135deg, #ffffff 0%, #94a3b8 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        .subtitle {
            color: var(--text-muted);
            font-size: 15px;
        }
        .grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 24px;
        }
        @media(max-width: 768px) { .grid { grid-template-columns: 1fr; } }
        .card {
            background: var(--card);
            border: 1px solid var(--card-border);
            border-radius: 16px;
            padding: 24px;
            box-shadow: 0 10px 25px -5px rgba(0,0,0,0.3);
        }
        .card-title {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 18px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .transport-options {
            display: flex;
            flex-direction: column;
            gap: 12px;
            margin-bottom: 20px;
        }
        .option-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 14px;
            border-radius: 12px;
            border: 2px solid var(--card-border);
            background: rgba(15, 23, 42, 0.5);
            cursor: pointer;
            transition: all 0.2s ease;
        }
        .option-item:hover {
            border-color: #475569;
            transform: translateY(-2px);
        }
        .option-item input[type="radio"] {
            accent-color: var(--primary);
            width: 18px;
            height: 18px;
        }
        .option-item.selected {
            border-color: var(--primary);
            background: rgba(37, 99, 235, 0.12);
        }
        .option-info { flex: 1; }
        .option-name { font-weight: 600; font-size: 15px; }
        .option-creator { font-size: 12px; color: var(--text-muted); font-family: 'JetBrains Mono', monospace; }
        .input-group {
            margin-bottom: 20px;
        }
        .input-group label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: var(--text-muted);
            margin-bottom: 8px;
        }
        .input-group input {
            width: 100%;
            padding: 12px 16px;
            background: #0f172a;
            border: 1px solid var(--card-border);
            border-radius: 10px;
            color: #fff;
            font-size: 14px;
            font-family: 'JetBrains Mono', monospace;
            outline: none;
        }
        .input-group input:focus { border-color: var(--primary); }
        .btn-dispatch {
            width: 100%;
            background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%);
            color: white;
            border: none;
            padding: 14px;
            border-radius: 12px;
            font-size: 15px;
            font-weight: 700;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            box-shadow: 0 4px 14px rgba(37, 99, 235, 0.4);
            transition: all 0.2s;
        }
        .btn-dispatch:hover {
            opacity: 0.95;
            transform: translateY(-1px);
        }
        .log-box {
            background: #090d16;
            border: 1px solid #1e293b;
            border-radius: 12px;
            height: 420px;
            overflow-y: auto;
            padding: 16px;
            font-family: 'JetBrains Mono', monospace;
            font-size: 13px;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }
        .log-entry {
            background: rgba(30, 41, 59, 0.7);
            border-left: 4px solid var(--primary);
            border-radius: 6px;
            padding: 12px;
            animation: fadeIn 0.3s ease;
        }
        .log-entry.truck { border-left-color: var(--accent-truck); }
        .log-entry.ship  { border-left-color: var(--accent-ship); }
        .log-entry.plane { border-left-color: var(--accent-plane); }
        .log-header {
            display: flex;
            justify-content: space-between;
            font-size: 11px;
            color: var(--text-muted);
            margin-bottom: 6px;
        }
        .log-body { line-height: 1.5; }
        .highlight { color: #38bdf8; font-weight: 600; }
        .tag {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 11px;
            font-weight: 600;
            background: rgba(255,255,255,0.1);
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(6px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .diagram-section {
            margin-top: 24px;
            background: var(--card);
            border: 1px solid var(--card-border);
            border-radius: 16px;
            padding: 20px 24px;
        }
        .diagram-content {
            background: #090d16;
            padding: 16px;
            border-radius: 10px;
            font-family: 'JetBrains Mono', monospace;
            font-size: 12px;
            color: #94a3b8;
            overflow-x: auto;
            white-space: pre;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <div class="badge">Creational Design Patterns • Refactoring Guru</div>
            <h1>Logistics Dispatcher Web Demo</h1>
            <p class="subtitle">Minh họa thực tế mô hình Factory Method Pattern trên trình duyệt</p>
        </header>

        <div class="grid">
            <!-- Cột trái: Điều khiển (Client UI) -->
            <div class="card">
                <div class="card-title">
                    <span>🎮 Bảng điều khiển (Client Input)</span>
                </div>

                <div class="transport-options">
                    <label class="option-item selected" onclick="selectOption('ROAD', this)">
                        <input type="radio" name="mode" value="ROAD" checked>
                        <span style="font-size: 24px;">🚚</span>
                        <div class="option-info">
                            <div class="option-name">Đường bộ (Road Logistics)</div>
                            <div class="option-creator">Creator: RoadLogistics ➔ Product: Truck</div>
                        </div>
                    </label>

                    <label class="option-item" onclick="selectOption('SEA', this)">
                        <input type="radio" name="mode" value="SEA">
                        <span style="font-size: 24px;">🚢</span>
                        <div class="option-info">
                            <div class="option-name">Đường biển (Sea Logistics)</div>
                            <div class="option-creator">Creator: SeaLogistics ➔ Product: Ship</div>
                        </div>
                    </label>

                    <label class="option-item" onclick="selectOption('AIR', this)">
                        <input type="radio" name="mode" value="AIR">
                        <span style="font-size: 24px;">✈️</span>
                        <div class="option-info">
                            <div class="option-name">Hàng không (Air Logistics - OCP Demo)</div>
                            <div class="option-creator">Creator: AirLogistics ➔ Product: Plane</div>
                        </div>
                    </label>
                </div>

                <div class="input-group">
                    <label for="shipmentId">MÃ KIỆN HÀNG (SHIPMENT ID):</label>
                    <input type="text" id="shipmentId" value="PKG-2026-888" placeholder="Nhập mã kiện hàng...">
                </div>

                <button class="btn-dispatch" onclick="dispatchOrder()">
                    <span>🚀 Thực thi Factory Method</span>
                </button>
            </div>

            <!-- Cột phải: Live Console Log -->
            <div class="card">
                <div class="card-title" style="justify-content: space-between;">
                    <span>📜 Console Output (Thời gian thực)</span>
                    <button onclick="clearLogs()" style="background:none; border:1px solid #475569; color:#94a3b8; border-radius:6px; padding:3px 8px; font-size:11px; cursor:pointer;">Xóa Log</button>
                </div>
                <div class="log-box" id="logBox">
                    <div style="color: #64748b; text-align: center; margin-top: 150px;" id="emptyHint">
                        Chọn phương thức và bấm nút "Thực thi Factory Method" để xem kết quả điều phối...
                    </div>
                </div>
            </div>
        </div>

        <!-- Sơ đồ tóm tắt kiến trúc bên dưới -->
        <div class="diagram-section">
            <div class="card-title">
                <span>📐 Kiến trúc Factory Method đang hoạt động trong Demo:</span>
            </div>
            <div class="diagram-content">
[Web Client UI] ──(gọi)──> [Logistics] (Creator Abstract)
                               │
               ┌───────────────┼───────────────┐
               ▼               ▼               ▼
        [RoadLogistics]  [SeaLogistics]  [AirLogistics]  (Concrete Creators)
               │ (creates)     │ (creates)     │ (creates)
               ▼               ▼               ▼
            [Truck]         [Ship]          [Plane]      (Concrete Products)
               └───────────────┼───────────────┘
                               ▼ (implements)
                          [Transport]                    (Product Interface)</div>
        </div>
    </div>

    <script>
        let currentMode = 'ROAD';

        function selectOption(mode, element) {
            currentMode = mode;
            document.querySelectorAll('.option-item').forEach(el => el.classList.remove('selected'));
            element.classList.add('selected');
            element.querySelector('input').checked = true;
        }

        async function dispatchOrder() {
            const shipmentId = document.getElementById('shipmentId').value.trim() || ('PKG-' + Math.floor(Math.random() * 90000 + 10000));
            const emptyHint = document.getElementById('emptyHint');
            if (emptyHint) emptyHint.remove();

            try {
                const response = await fetch(`/api/dispatch?mode=${currentMode}&id=${encodeURIComponent(shipmentId)}`);
                const data = await response.json();

                const logBox = document.getElementById('logBox');
                const entry = document.createElement('div');
                const cssClass = data.product.toLowerCase();
                entry.className = `log-entry ${cssClass}`;

                entry.innerHTML = `
                    <div class="log-header">
                        <span>⏰ ${data.time}</span>
                        <span class="tag">Mã: #${data.shipmentId}</span>
                    </div>
                    <div class="log-body">
                        <div>🔷 <b>Creator kích hoạt:</b> <span class="highlight">${data.creator}</span></div>
                        <div>⚙️ <b>Factory Method:</b> <code>${data.creator}.createTransport()</code></div>
                        <div>📦 <b>Product sinh ra:</b> <span class="highlight">${data.product}</span> (implements Transport)</div>
                        <div style="margin-top:6px; color:#e2e8f0;">${data.deliveryMessage}</div>
                    </div>
                `;

                logBox.prepend(entry); // Hiện log mới nhất lên đầu
            } catch (err) {
                alert('Lỗi kết nối tới máy chủ Java!');
            }
        }

        function clearLogs() {
            document.getElementById('logBox').innerHTML = '<div style="color: #64748b; text-align: center; margin-top: 150px;" id="emptyHint">Đã xóa log. Hãy bấm nút để điều phối tiếp!</div>';
        }
    </script>
</body>
</html>
""";
            byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    /**
     * Handler API: Nhận yêu cầu từ Web -> Kích hoạt Factory Method chuẩn Java -> Trả JSON
     */
    static class DispatchApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Lấy tham số query (mode & id)
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = parseQuery(query);

            String mode = params.getOrDefault("mode", "ROAD");
            String shipmentId = params.getOrDefault("id", "PKG-2026-DEFAULT");

            // 1. ÁP DỤNG FACTORY METHOD
            Logistics logistics = switch (mode.toUpperCase()) {
                case "ROAD" -> new RoadLogistics();
                case "SEA"  -> new SeaLogistics();
                case "AIR"  -> new AirLogistics();
                default     -> new RoadLogistics();
            };

            // 2. Kích hoạt Factory Method để lấy Product
            Transport transport = logistics.createTransport();

            String deliveryMsg = "";
            if (transport instanceof Truck) {
                deliveryMsg = "🚚 <b>[Truck]:</b> Đã xuất kho! Hàng được vận chuyển bằng đường bộ trong thùng xe tải trọng tải lớn.";
            } else if (transport instanceof Ship) {
                deliveryMsg = "🚢 <b>[Ship]:</b> Đã xếp container! Hàng được chuyển qua đường biển quốc tế.";
            } else if (transport instanceof Plane) {
                deliveryMsg = "✈️ <b>[Plane]:</b> Đã cất cánh! Hàng được giao hỏa tốc xuyên quốc gia qua đường hàng không.";
            }

            String timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Trả JSON
            String json = String.format("""
                {
                    "time": "%s",
                    "shipmentId": "%s",
                    "creator": "%s",
                    "product": "%s",
                    "deliveryMessage": "%s"
                }
            """, timeStr, shipmentId, logistics.getClass().getSimpleName(), transport.getClass().getSimpleName(), deliveryMsg);

            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private Map<String, String> parseQuery(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null) return result;
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length > 1) {
                    result.put(pair[0], java.net.URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
                }
            }
            return result;
        }
    }
}

