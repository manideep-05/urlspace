import { Link } from "react-router-dom";
import { logout, isAdmin } from "../auth/auth";

export default function Navbar() {
  return (
    <nav style={styles.nav}>
      <h3>UrlSpace</h3>

      <div style={styles.links}>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/analytics">Analytics</Link>

        {isAdmin() && (
          <Link to="/admin/alerts">Admin Alerts</Link>
        )}

        <button onClick={logout} style={styles.logout}>Logout</button>
      </div>
    </nav>
  );
}

const styles = {
  nav: {
    display: "flex",
    justifyContent: "space-between",
    padding: "12px 20px",
    background: "#222",
    color: "white",
  },
  links: {
    display: "flex",
    gap: "15px",
    alignItems: "center",
  },
  logout: {
    background: "red",
    color: "white",
    border: "none",
    padding: "6px 10px",
    cursor: "pointer",
  },
};
