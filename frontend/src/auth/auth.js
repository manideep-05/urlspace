export const getToken = () => localStorage.getItem("token");

export const logout = () => {
  localStorage.removeItem("token");
  window.location.href = "/login";
};

// simple role check (backend enforces real security)
export const isAdmin = () => {
  const token = getToken();
  return token && token.includes("ADMIN"); // demo-level
};
