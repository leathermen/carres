class ApiError extends Error {
  public message: string;
  public code: string;

  constructor(message: string, code: string) {
    super(message);
    this.name = "ApiError";
    this.message = message;
    this.code = code;
  }
}

export { ApiError };
