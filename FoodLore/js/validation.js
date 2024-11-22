document.getElementById('register-form').addEventListener('submit', function (event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const username = document.getElementById('email').value;
    const password = document.getElementById('pass').value;
    const confirmPassword = document.getElementById('re_pass').value;
    const usertype = document.getElementById('role').value;

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(username)) {
        alert('Please enter a valid email address');
        return;
    }

    if (password !== confirmPassword) {
        alert('Passwords do not match');
        return;
    }

    if (usertype !== 'customer' && usertype !== 'admin') {
        alert('Please select a valid user type');
        return;
    }

    this.submit();
});
