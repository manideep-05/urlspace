import { BrowserRouter, Routes, Route } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import AdminAlerts from "./pages/AdminAlerts";
import AdminRoute from "./components/AdminRoute";
import Analytics from "./pages/Analytics";
import Layout from "./components/Layout";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Layout>
                <Dashboard />
              </Layout>
            </ProtectedRoute>
          }
        />

        <Route
          path="/analytics"
          element={
            <ProtectedRoute>
              <Layout>
                <Analytics />
              </Layout>
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/alerts"
          element={
            <AdminRoute>
              <Layout>
                <AdminAlerts />
              </Layout>
            </AdminRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
