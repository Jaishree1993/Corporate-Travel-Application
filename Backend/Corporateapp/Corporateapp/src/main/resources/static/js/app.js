document.getElementById("searchForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const from = document.getElementById("from").value.trim();
  const to = document.getElementById("to").value.trim();
 const rawDate = document.getElementById("date").value; // already yyyy-MM-dd
const formattedDate = rawDate;





  const resultsDiv = document.getElementById("results");
  resultsDiv.innerHTML = "<p>Loading flights...</p>";


  fetch(`/api/search/flights?from=${from}&to=${to}&date=${formattedDate}`)
    .then(response => {
      if (!response.ok) throw new Error("Network response was not ok");
      return response.json();
    })
    .then(data => {
      resultsDiv.innerHTML = "";

      if (!data || data.length === 0) {
        resultsDiv.innerHTML = "<p>No flights found.</p>";
        return;
      }

      const table = document.createElement("table");
      table.innerHTML = `
        <thead>
          <tr>
            <th>Airline</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Date</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Price</th>
            <th>Seats</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          ${data.map(flight => `
            <tr>
              <td>${flight.airline}</td>
              <td>${flight.origin}</td>
              <td>${flight.destination}</td>
              <td>${flight.date}</td>
              <td>${flight.departure_time}</td>
              <td>${flight.arrival_time}</td>
              <td>₹${flight.price}</td>
              <td>${flight.seats_available}</td>
      <td><button onclick="redirectToBooking(${flight.flight_id})">Continue</button></td>            </tr>
          `).join("")}
        </tbody>
      `;
      resultsDiv.appendChild(table);
    })
    .catch(error => {
      console.error("Error fetching flights:", error);
      resultsDiv.innerHTML = "<p>Error loading flights.</p>";
    });
});
function redirectToBooking(flightId) {
  window.location.href = `/booking-form.html?flightId=${flightId}`;
}


















/*document.getElementById('searchForm').addEventListener('submit', async (e) => {
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
}*/
