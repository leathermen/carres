import { FormEvent, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';


interface CarReservationFormProps {
  onSubmit: (startTime: Date, durationMinutes: number) => Promise<void>;
}

export default function CarReservationForm({ onSubmit }: CarReservationFormProps) {
  const [startDate, setStartDate] = useState<Date>(getStartOfTomorrow());
  const [durationMinutes, setDurationMinutes] = useState<number>(15);

  const [submitted, setSubmitted] = useState<boolean>(false);
  const [submitError, setSubmitError] = useState<string | null>(null);

  const hours = Array.from({ length: 10 }, (_, index) => index + 9);
  const minutes = [0, 15, 30, 45];
  const durations = [15, 30, 45, 60];


  const onSubmitInner = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    onSubmit(startDate, durationMinutes).then(() => setSubmitted(true)).catch(() => setSubmitError("Something went wrong!"));
  }

  return (
    <Form onSubmit={(event: FormEvent<HTMLFormElement>) => onSubmitInner(event)}>
      {submitted && (<>
        <div className="alert alert-success" role="alert">
          Successfully submitted
          <br />
          <sub>You will be redirected soon...</sub>
        </div>
      </>)}
      {!!submitError && (<>
        <div className="alert alert-danger" role="alert">
          Something went wrong, see console
        </div>
      </>)}
      {!submitted && !submitError && (
        <>
          <Form.Group className='mt-2' controlId="datepicker">
            <Form.Label>Date</Form.Label>
            <DatePicker
              selected={startDate}
              onChange={(date: Date) => {
                setStartDate(date);
              }}
              dateFormat="MM/dd/yyyy"
              className="form-control"
              minDate={getStartOfTomorrow()}
              filterDate={filterWeekends}
              locale="en-US"
            />
          </Form.Group>
          <Form.Group className='mt-2' controlId="timepicker">
            <Form.Label>Time</Form.Label>
            <div className="d-flex">
              <Form.Select
                className="me-2"
                value={startDate.getHours()}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  const newDate = new Date(startDate);
                  newDate.setHours(Number(e.target.value));
                  setStartDate(newDate);
                }}
              >
                {hours.map(hour => (
                  <option key={hour} value={hour}>{hour.toString().padStart(2, '0')}</option>
                ))}
              </Form.Select>
              <Form.Select
                value={startDate.getMinutes()}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  const newDate = new Date(startDate);
                  newDate.setMinutes(Number(e.target.value));
                  setStartDate(newDate);
                }}
              >
                {minutes.map(minute => (
                  <option key={minute} value={minute}>{minute.toString().padStart(2, '0')}</option>
                ))}
              </Form.Select>
            </div>
          </Form.Group>
          <Form.Group className='mt-3' controlId="timepicker">
            <Form.Label>Duration: {durationMinutes}</Form.Label>
            <div className="d-flex">
              <Form.Select
                value={durationMinutes}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  setDurationMinutes(Number(e.target.value));
                }}
              >
                {durations.map(duration => (
                  <option key={duration} value={duration}>{duration.toString().padStart(2, '0')}</option>
                ))}
              </Form.Select>
            </div>
          </Form.Group>
          <Button className="mt-4" variant="primary" type="submit">
            Submit
          </Button>
        </>
      )}
    </Form >
  );
}

const getStartOfTomorrow = (): Date => {
  const now = new Date();
  const tomorrow = new Date(now);
  tomorrow.setDate(now.getDate() + 1);
  tomorrow.setHours(9, 0, 0, 0);
  return tomorrow;
}

const filterWeekends = (date: Date) => {
  const day = date.getDay();
  return day !== 0 && day !== 6;
};