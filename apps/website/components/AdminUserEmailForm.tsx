import { Alert, Button, Col, Form, InputGroup, Row } from 'react-bootstrap';
import { getUserIdByEmail } from '../utils/client/apiClient';
import { NotFoundError } from '../utils/client/errors/NotFoundError';
import { useEffect, useState } from 'react';

interface AdminUserEmailProps {
  onSubmit: (id: string) => void;
}

export default function AdminUserEmailForm({ onSubmit }: AdminUserEmailProps) {

  const [error, setError] = useState<string | null>(null);
  const [userId, setUserId] = useState<string | null>(null);
  const [lastSubmitted, setLastSubmitted] = useState<string | null>(null);

  return <Form onSubmit={async (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      setLastSubmitted(e.target.userEmail.value);
      const user = await getUserIdByEmail(e.target.userEmail.value);
      setError(null);
      onSubmit(user.id);
    } catch (e) {
      if (e instanceof NotFoundError) {
        setError(e.message);
        return;
      }

      throw e;
    }
  }}>
    {error != null && (
      <Row className="align-items-center">
        <Col>
          <Alert variant={'warning'}>
            {error}
          </Alert>
        </Col>
      </Row>
    )}
    <Row className="align-items-center">
      <Col xs={9} xl={6} className="my-1">
        <Form.Label htmlFor="userEmail" visuallyHidden>
          User's email address
        </Form.Label>
        <Form.Control onChange={(e: React.ChangeEvent<HTMLInputElement>) => setUserId(e.target.value)} id="userEmail" placeholder="email@example.com" />
      </Col>

      <Col xs={3} xl={2} className="my-1">
        <Button disabled={lastSubmitted === userId} type="submit">Reload</Button>
      </Col>
    </Row>
  </Form>;
}