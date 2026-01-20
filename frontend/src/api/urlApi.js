import api from "./axios";

export const createShortUrl = async (longUrl) => {
  const res = await api.post("/url/shorten", { longUrl });
  return res.data;
};

export const getMyUrls = async () => {
  const res = await api.get("/url/my");
  return res.data;
};
