import api from "./axios";

export const getClicksPerUrl = async () => {
  const res = await api.get("/analytics/clicks-per-url");
  return res.data;
};

export const getClicksPerDay = async () => {
  const res = await api.get("/analytics/clicks-per-day");
  return res.data;
};
