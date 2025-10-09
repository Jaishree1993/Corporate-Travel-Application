document.addEventListener("DOMContentLoaded", () => {
  // Grab flightId from query string
  const params = new URLSearchParams(window.location.search);
  const flightId = params.get("flightId");
  document.getElementById("flightId").value = flightId;

  // Load selected flight from localStorage
  const flightData = JSON.parse(localStorage.getItem('selectedFlight') || '{}');

  // Fill booking summary if flight matches
  if (flightData && flightData.flight_id === flightId) {
    document.querySelector('.airline').textContent = flightData.airline;
    document.querySelector('.flight-time').textContent = `${flightData.departure_time} - ${flightData.arrival_time}`;
    document.querySelector('.flight-details').textContent = `${flightData.origin} to ${flightData.destination}`;
    document.querySelector('.total-price').textContent = `₹${flightData.price}`;
    document.querySelector('.price-breakdown').innerHTML = `
      <p>Base Fare <span>₹${flightData.price}</span></p>
      <p>Taxes & Fees <span>₹0</span></p>
      <p class="grand-total">Total Price <span>₹${flightData.price}</span></p>
    `;
  }

  // Handle booking form submission
  document.getElementById("bookingForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const booking = {
      flightId: flightId,
      firstName: document.getElementById("firstName1").value,
      lastName: document.getElementById("lastName1").value,
      dob: document.getElementById("dob1").value,
      gender: document.getElementById("gender1").value,
      nationality: document.getElementById("nationality1").value,
      phone: document.getElementById("phone1").value,
      passportNumber: document.getElementById("passportNumber1").value,
      passportExpiry: document.getElementById("passportExpiry1").value
    };

    fetch("/api/book", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(booking)
    })
    .then(res => res.json())
    .then(data => {
      // Hide the form
      document.getElementById("bookingForm").style.display = "none";

      // Show styled confirmation
      const msgDiv = document.getElementById("confirmationMessage");
      msgDiv.style.display = "block";
      msgDiv.innerHTML = `
        🎉 Congrats, ${booking.firstName}!<br>
        Your booking is confirmed.<br>
        <span style="font-size:14px; font-weight:400;">Booking ID: ${data.booking_id}</span>
        <div style="margin-top:15px;">
          <a href="/index.html" style="padding:10px 16px; background:#2563eb; color:#fff; border-radius:6px; text-decoration:none;">Back to Home</a>
        </div>
      `;

      // Clear stored flight
      localStorage.removeItem('selectedFlight');
    })
    .catch(err => {
      console.error("Booking failed:", err);
      alert("Booking failed. Please try again.");
    });
  });
});
