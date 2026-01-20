import { Navigate } from "react-router-dom";

export default function AdminRoute({ children }) {
  const token = localStorage.getItem("token");

  // simple check for demo (backend enforces real security)
  return token ? children : <Navigate to="/login" />;
}
