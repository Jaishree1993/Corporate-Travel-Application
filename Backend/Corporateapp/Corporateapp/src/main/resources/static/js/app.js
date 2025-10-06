document.getElementById("searchForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const from = document.getElementById("from").value.trim();
  const to = document.getElementById("to").value.trim();
  const rawDate = document.getElementById("date").value;
  const formattedDate = rawDate;

  const resultsDiv = document.getElementById("results");
  resultsDiv.innerHTML = "<p>Loading flights...</p>";

  const url = `/api/search/flights?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}&date=${encodeURIComponent(formattedDate)}`;

  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error("Network response was not ok: " + response.status);
      return response.json();
    })
    .then(data => {
      resultsDiv.innerHTML = "";

      if (!data || data.length === 0) {
        resultsDiv.innerHTML = "<p>No flights found.</p>";
        return;
      }

resultsDiv.innerHTML = data.map(flight => `
  <div class="flight-card" data-flight-id="${flight.flight_id || flight.id || ''}">
    <div class="left">
      <div class="logo">${(flight.airline || 'XX').slice(0, 2).toUpperCase()}</div>
      <div class="meta">
        <div class="route">${flight.origin || ''} → ${flight.destination || ''}</div>
        <div class="time">${flight.departure_time || ''} — ${flight.arrival_time || ''} • ${flight.duration || '1h 19m'}</div>
        <div class="details">
          <span class="badge">${flight.stops === 0 ? 'Non-stop' : `${flight.stops}+ Stops`}</span>
          <span class="miles">${flight.distance || '—'} mi</span>
          <span class="date">${flight.date || ''}</span>
          <span class="seats">${flight.seats_available || flight.seats || ''} seats</span>
        </div>
      </div>
    </div>
    <div class="price">
      <div class="amt">₹${flight.price || ''}</div>
      <button class="btn-select-flight">Continue</button>
    </div>
  </div>
`).join("");

      document.querySelectorAll('.btn-select-flight').forEach(btn => {
  btn.addEventListener('click', function () {
    const card = this.closest('.flight-card');
    const fid = card && card.getAttribute('data-flight-id');
    if (fid) redirectToBooking(fid);
  });
});

    })
    .catch(error => {
      console.error("Error fetching flights:", error);
      resultsDiv.innerHTML = "<p>Error loading flights.</p>";
    });
});

function redirectToBooking(flightId) {
  const url = `/booking-form.html?flightId=${encodeURIComponent(flightId)}`;
  window.location.href = url;
}





/*document.getElementById("searchForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const from = document.getElementById("from").value.trim();
  const to = document.getElementById("to").value.trim();
  const rawDate = document.getElementById("date").value; // yyyy-MM-dd
  const formattedDate = rawDate;

  const resultsDiv = document.getElementById("results");
  resultsDiv.innerHTML = "<p>Loading flights...</p>";

  const url = `/api/search/flights?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}&date=${encodeURIComponent(formattedDate)}`;

  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error("Network response was not ok: " + response.status);
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
            <tr data-flight-id="${flight.flight_id || flight.id || ''}">
              <td>${flight.airline || ''}</td>
              <td>${flight.origin || flight.from || ''}</td>
              <td>${flight.destination || flight.to || ''}</td>
              <td>${flight.date || ''}</td>
              <td>${flight.departure_time || ''}</td>
              <td>${flight.arrival_time || ''}</td>
              <td>₹${flight.price || ''}</td>
              <td>${flight.seats_available || flight.seats || ''}</td>
              <td><button class="continue-btn">Continue</button></td>
            </tr>
          `).join("")}
        </tbody>
      `;
      resultsDiv.appendChild(table);

      // Attach click handlers for Continue buttons
      document.querySelectorAll('.continue-btn').forEach(btn => {
        btn.addEventListener('click', function () {
          const tr = this.closest('tr');
          const fid = tr && tr.getAttribute('data-flight-id');
          if (fid) {
            redirectToBooking(fid);
          }
        });
      });
    })
    .catch(error => {
      console.error("Error fetching flights:", error);
      resultsDiv.innerHTML = "<p>Error loading flights.</p>";
    });
});

function redirectToBooking(flightId) {
  // encode flightId though usually numeric
  const url = `/booking-form.html?flightId=${encodeURIComponent(flightId)}`;
  window.location.href = url;
}
*/


















