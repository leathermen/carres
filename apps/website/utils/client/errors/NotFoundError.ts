class NotFoundError extends Error {
  public message: string;
  public code: string;

  constructor(message: string, code: string) {
    super(message);
    this.name = "NotFoundError";
    this.message = message;
    this.code = code;
  }
}

export { NotFoundError };
