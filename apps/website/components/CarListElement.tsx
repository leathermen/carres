import 'react';

interface CarListElementProps {
  id: string;
  manufacturer: string;
  model: string;
  manufacturedAt: string;
}

export default function CarListElement({ id, manufacturer, model, manufacturedAt }: CarListElementProps) {

  const manufacturedAtDate = new Date(manufacturedAt);

  return <li className="list-group-item d-flex justify-content-between align-items-start">
    <div className="ms-2 me-auto w-100">
      <div className='row'>
        <div className="col-3 col-lg-2">
          <a href={`/create-reservation/${id}`} className="btn btn-light btn-success:hover">Order</a>
        </div>
        <div className="col-9 col-lg-10">
          <span className="fw-bold">{manufacturer}</span> <span>{model}</span>
          <p>Manufactured on: <em>{manufacturedAtDate.toLocaleDateString('en-US', { day: 'numeric', month: 'long', year: 'numeric' })}</em></p>
        </div>
      </div>
    </div>
    <span className={`badge bg-success rounded-pill`}>Available</span>
  </li>;
}