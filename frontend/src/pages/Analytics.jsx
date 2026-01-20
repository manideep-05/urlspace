import { useEffect, useState } from "react";
import { Bar, Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  LineElement,
  PointElement,
  Tooltip,
  Legend,
} from "chart.js";
import { getClicksPerUrl, getClicksPerDay } from "../api/analyticsApi";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  LineElement,
  PointElement,
  Tooltip,
  Legend
);

export default function Analytics() {
  const [perUrl, setPerUrl] = useState([]);
  const [perDay, setPerDay] = useState([]);

  useEffect(() => {
    getClicksPerUrl().then(setPerUrl);
    getClicksPerDay().then(setPerDay);
  }, []);

  const urlChart = {
    labels: perUrl.map(u => u.shortCode),
    datasets: [
      {
        label: "Clicks per URL",
        data: perUrl.map(u => u.clicks),
      },
    ],
  };

  const dayChart = {
    labels: perDay.map(d => d.date),
    datasets: [
      {
        label: "Clicks per Day",
        data: perDay.map(d => d.clicks),
      },
    ],
  };

  return (
    <div>
      <h2>📊 Analytics Dashboard</h2>

      <h3>Clicks per URL</h3>
      <Bar data={urlChart} />

      <h3>Clicks per Day</h3>
      <Line data={dayChart} />
    </div>
  );
}
