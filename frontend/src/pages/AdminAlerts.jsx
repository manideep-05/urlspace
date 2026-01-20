import { useEffect, useState } from "react";
import { connectAlertSocket, disconnectAlertSocket } from "../websocket/alertSocket";
import api from "../api/axios";

export default function AdminAlerts() {
  const [alerts, setAlerts] = useState([]);

  // Fetch historical alerts
  const fetchAlerts = async () => {
    try {
      const res = await api.get("/admin/alerts");
      setAlerts(res.data.reverse());
    } catch (err) {
      console.error("Failed to fetch alerts");
    }
  };

  useEffect(() => {
    fetchAlerts();

    connectAlertSocket((alert) => {
      setAlerts(prev => [alert, ...prev]);
    });

    return () => {
      disconnectAlertSocket();
    };
  }, []);

  return (
    <div>
      <h2>🚨 Admin Live Traffic Alerts</h2>

      {alerts.length === 0 && <p>No alerts yet</p>}

      {alerts.map((a, i) => (
        <div
          key={i}
          style={{
            border: "1px solid red",
            padding: "10px",
            marginBottom: "8px",
            borderRadius: "5px"
          }}
        >
          <b>Short Code:</b> {a.shortCode} <br />
          <b>Clicks:</b> {a.clickCount} <br />
          <b>Time:</b> {a.timestamp}
        </div>
      ))}
    </div>
  );
}
