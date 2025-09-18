document.getElementById('searchForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const from = document.getElementById('from').value.trim();
  const to = document.getElementById('to').value.trim();
  const date = document.getElementById('date').value;
  const url = `/api/search/flights?from=${from}&to=${to}&date=${date}`;
  const res = await fetch(url);
  const flights = await res.json();
  const container = document.getElementById('results');
  if (!flights || flights.length === 0) {
    container.innerHTML = '<p>No flights found.</p>';
    return;
  }
  let html = '<table><tr><th>Airline</th><th>From</th><th>To</th><th>Date</th><th>Price</th><th>Action</th></tr>';
  flights.forEach(f => {
    html += `<tr>
      <td>${f.airline}</td>
      <td>${f.fromAirport}</td>
      <td>${f.toAirport}</td>
      <td>${f.departureDate}</td>
      <td>${f.price}</td>
      <td><button onclick="book(${f.id}, ${f.price})">Book</button></td>
    </tr>`;
  });
  html += '</table>';
  container.innerHTML = html;
});

async function book(flightId, price) {
  const passengerName = prompt("Passenger name?");
  if (!passengerName) return;
  const payload = { flightId, passengerName, amount: price };
  const res = await fetch('/api/book', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(payload)
  });
  const booking = await res.json();
  alert('Booked! Booking ID: ' + booking.id);
}
