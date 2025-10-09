// Reusable search function
function runSearch() {
  const from = document.getElementById("from").value.trim();
  const to = document.getElementById("to").value.trim();
  const rawDate = document.getElementById("date").value;
  const formattedDate = rawDate;

  const resultsDiv = document.getElementById("results");
  resultsDiv.innerHTML = "<p>Loading flights...</p>";

  // Collect filters
  const maxPrice = document.getElementById("priceRange").value;

  let stops = document.querySelector('input[name="stops"]:checked')?.value;
  if (!stops || stops === "") stops = null;

  let departureTime = document.querySelector('input[name="departureTime"]:checked')?.value;
  if (!departureTime || departureTime === "") departureTime = null;

  // Debug log
  console.log("DEBUG stops:", stops, "departureTime:", departureTime);

  // Build URL
  let url = `/api/search/flights?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}&date=${encodeURIComponent(formattedDate)}`;
  if (maxPrice && maxPrice !== "0") url += `&maxPrice=${maxPrice}`;
  if (stops !== null) url += `&stops=${stops}`;
  if (departureTime !== null) url += `&departureTime=${departureTime}`;

  console.log("DEBUG URL:", url);

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
        <div class="flight-card" data-flight-id="${flight.flight_id}">
          <div class="left">
            <div class="logo">${(flight.airline || 'XX').slice(0, 2).toUpperCase()}</div>
            <div class="meta">
              <div class="route">${flight.origin} → ${flight.destination}</div>
              <div class="time">${flight.departure_time} — ${flight.arrival_time}</div>
              <div class="details">
                <span class="badge">${flight.stops === 0 ? 'Non-stop' : flight.stops + ' Stop(s)'}</span>
                <span class="seats">${flight.seats_available} seats</span>
              </div>
            </div>
          </div>
          <div class="price">
            <div class="amt">₹${flight.price}</div>
            <button class="btn-select-flight">Continue</button>
          </div>
        </div>
      `).join("");

      // Continue button → booking form
      document.querySelectorAll('.btn-select-flight').forEach(btn => {
        btn.addEventListener('click', function () {
          const card = this.closest('.flight-card');
          const fid = card.getAttribute('data-flight-id');

          const flightData = {
            flight_id: fid,
            airline: card.querySelector('.logo').textContent,
            origin: card.querySelector('.route').textContent.split('→')[0].trim(),
            destination: card.querySelector('.route').textContent.split('→')[1].trim(),
            departure_time: card.querySelector('.time').textContent.split('—')[0].trim(),
            arrival_time: card.querySelector('.time').textContent.split('—')[1].trim(),
            price: card.querySelector('.amt').textContent.replace(/[₹,]/g, '').trim()
          };

          localStorage.setItem('selectedFlight', JSON.stringify(flightData));
          window.location.href = `/booking-form.html?flightId=${encodeURIComponent(fid)}`;
        });
      });
    })
    .catch(err => {
      console.error("Error fetching flights:", err);
      resultsDiv.innerHTML = "<p>Error loading flights.</p>";
    });
}

// Hook up the form submit
document.getElementById("searchForm").addEventListener("submit", function (e) {
  e.preventDefault();
  runSearch();
});

// Hook up filter change events
document.getElementById("priceRange").addEventListener("input", runSearch);
document.querySelectorAll('input[name="stops"]').forEach(r => r.addEventListener("change", runSearch));
document.querySelectorAll('input[name="departureTime"]').forEach(r => r.addEventListener("change", runSearch));

// Price slider UI update
const priceRange = document.getElementById('priceRange');
const priceValue = document.getElementById('priceValue');
if (priceRange && priceValue) {
  priceRange.oninput = function () {
    priceValue.textContent = this.value;
  };
}

// Modify search buttons
document.getElementById('modifySearchInline').addEventListener('click', () => {
  document.getElementById('from').focus();
});
document.getElementById('modifySearchTop').addEventListener('click', () => {
  document.getElementById('from').focus();
});
