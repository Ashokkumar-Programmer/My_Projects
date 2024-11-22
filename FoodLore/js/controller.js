const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');
const path = require('path');
const cookieParser = require('cookie-parser');

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, '../')));
app.use(express.json());
app.use(cookieParser());

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'foodlore'
});

db.connect((err) => {
    if (err) throw err;
    console.log('Connected to database');
});

app.get('/signup', (req, res) => {
    res.sendFile(path.join(__dirname, '../signup.html'));
});

app.get('/addfood', (req, res) => {
    res.sendFile(path.join(__dirname, '../addfood.html'));
});

app.get('/signin', (req, res) => {
    const username = req.cookies.foodloreUsername;
    if (username) {
        return res.redirect('/');
    }
    res.sendFile(path.join(__dirname, '../signin.html'));
});

app.get('/admin', (req, res) => {
    res.sendFile(path.join(__dirname, '../admin.html'));
});

app.get('/favourite', (req, res) => {
    const username = req.cookies.foodloreUsername;
    if (!username) {
        return res.redirect('/signin');
    }
    const filePath = path.join(__dirname, '../favourite.html');
    fs.readFile(filePath, 'utf-8', (err, data) => {
        if (err) {
            return res.status(500).send('An error occurred while reading the file.');
        }

        // Replace the placeholder with the actual username
        const modifiedHtml = data.replace('<p id="username"></p>', `<p id="username">${username}</p>`);

        // Send the modified HTML
        res.send(modifiedHtml);
    });
});

app.post('/create_account', (req, res) => {
    const { name, username, password, usertype } = req.body;
    const sql = 'INSERT INTO userdata (name, username, password, usertype) VALUES (?, ?, ?, ?)';
    db.query(sql, [name, username, password, usertype], (err, result) => {
        if (err && err.code === "ER_DUP_ENTRY") {
            return res.redirect('/signup?error=Username+already+exists');
        }
        if (err) {
            return res.status(500).send('An error occurred while creating the account');
        }
        return res.redirect('/signin');
    });
});

app.post('/login', (req, res) => {
    
    const { username, pass } = req.body;

    const sql = 'SELECT * FROM userdata WHERE username = ?';
    db.query(sql, [username], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred during login');
        }

        if (results.length > 0) {
            const { password, usertype, name } = results[0];
            if (pass === password) {
                if (usertype === 'admin') {
                    return res.redirect('/admin');
                } else if (usertype === 'customer') {
                    res.cookie('foodloreUsername', name, { maxAge: 172800000, httpOnly: true });
                    return res.redirect('/');
                }
            } else {
                return res.redirect('/signin?error=Incorrect+password');
            }
        } else {
            return res.redirect('/signin?error=Username+not+found');
        }
    });
});

app.post('/addfooditem', (req, res) => {
    const { fileurl, name, details } = req.body;
    let file = String(fileurl).trim();

    const isWebUrl = file.startsWith('http://') || file.startsWith('https://');

    if (!isWebUrl) {
        return res.redirect('/addfood?error1=Only+online+url+is+accepted');
    }
    const checkFoodName = 'SELECT * FROM foods WHERE name = ?';
    db.query(checkFoodName, [name], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while checking food name');
        }

        if (results.length > 0) {
            return res.redirect('/addfood?error=Food+already+exists');
        } else {
            const insertFood = 'INSERT INTO foods (imageUrl, name, description, favourite, comments) VALUES (?, ?, ?, 0, "")';
            db.query(insertFood, [file, name, details], (err, result) => {
                if (err) {
                    return res.status(500).send('An error occurred while adding the food details');
                }
                res.redirect('/admin');
            });
        }
    });
});

app.get('/', (req, res) => {
    const username = req.cookies.foodloreUsername;
    if (!username) {
        return res.redirect('/signin');
    }

    const filePath = path.join(__dirname, '../main.html');
    fs.readFile(filePath, 'utf-8', (err, data) => {
        if (err) {
            return res.status(500).send('An error occurred while reading the file.');
        }

        // Replace the placeholder with the actual username
        const modifiedHtml = data.replace('<p id="username"></p>', `<p id="username">${username}</p>`);

        // Send the modified HTML
        res.send(modifiedHtml);
    });
});

app.get('/logout', (req, res) => {
    res.clearCookie('foodloreUsername');
    return res.redirect('/signin');
  });

const fs = require('fs');
let searchName='';
app.get('/search', (req, res) => {
    searchName = req.query.name;
    const username = req.cookies.foodloreUsername;
    if (!username) {
        return res.redirect('/signin');
    }
    const filePath = path.join(__dirname, '../search.html');
    fs.readFile(filePath, 'utf-8', (err, data) => {
        if (err) {
            return res.status(500).send('An error occurred while reading the file.');
        }

        // Replace the placeholder with the actual username
        const modifiedHtml = data.replace('<p id="username"></p>', `<p id="username">${username}</p>`);

        // Send the modified HTML
        res.send(modifiedHtml);
    });
});

app.get('/getFoods', (req, res) => {
    const sql = 'SELECT * FROM foods';
    db.query(sql, (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while fetching food data');
        }
        res.json(results); // Send the results and username as JSON
    });
});


app.put('/updateFood/:id', (req, res) => {
    const { id } = req.params;
    const { description } = req.body;

    const sql = 'UPDATE foods SET description = ? WHERE id = ?';
    db.query(sql, [description, id], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while updating the food description');
        }
        res.send('Food description updated successfully');
    });
});

app.delete('/deleteFood/:id', (req, res) => {
    const { id } = req.params;

    const sql = 'DELETE FROM foods WHERE id = ?';
    db.query(sql, [id], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while deleting the food');
        }
        res.send('Food deleted successfully');
    });
});


app.get('/searchFoods', (req, res) => {
    const sql = 'SELECT * FROM foods WHERE name LIKE ?';
    db.query(sql, [`%${searchName}%`], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while fetching food data');
        }
        res.json(results); // Send the results as JSON
    });
});

app.get('/favouriteFoods', (req, res) => {
    const sql = 'SELECT * FROM foods WHERE favourite= ?';
    db.query(sql, ['1'], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while fetching food data');
        }
        res.json(results); // Send the results as JSON
    });
});

app.post('/addComment', (req, res) => {
    const { foodname, username, comment } = req.body;

    if (!foodname || !username || !comment) {
        return res.status(400).send('All fields are required');
    }

    // Get the current comments for the specified food
    const sqlGetComments = 'SELECT comments FROM foods WHERE name = ?';
    db.query(sqlGetComments, [foodname], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while fetching food details');
        }

        if (results.length > 0) {
            let comments = results[0].comments ? JSON.parse(results[0].comments) : [];

            // Add the new comment
            comments.push({ username: username, comment: comment });

            // Save the updated comments
            const sqlUpdateComments = 'UPDATE foods SET comments = ? WHERE name = ?';
            db.query(sqlUpdateComments, [JSON.stringify(comments), foodname], (err, updateResults) => {
                if (err) {
                    return res.status(500).send('An error occurred while saving the comment');
                }

                res.send('Comment added successfully');
            });
        } else {
            res.status(404).send('Food not found');
        }
    });
});

// Endpoint to display comments
app.get('/getComments', (req, res) => {
    const { foodname } = req.query;

    if (!foodname) {
        return res.status(400).send('Food name is required');
    }

    const sqlGetComments = 'SELECT comments FROM foods WHERE name = ?';
    db.query(sqlGetComments, [foodname], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while fetching comments');
        }

        if (results.length > 0) {
            let comments = results[0].comments ? JSON.parse(results[0].comments) : [];

            if (comments.length === 0) {
                return res.send('No comments yet');
            }

            // Format the comments for display
            let formattedComments = comments.map(c => `${c.username}: ${c.comment}`).join(' | ');

            res.send(formattedComments);
        } else {
            res.status(404).send('Food not found');
        }
    });
});

// Endpoint to display food details along with comments
app.get('/details', (req, res) => {
    const { foodname, username } = req.query;

    if (!foodname) {
        return res.status(400).send('Food name is required');
    }
    if (!username) {
        return res.status(400).send("Username is required");
    }

    const sql = 'SELECT * FROM foods WHERE name = ?';
    db.query(sql, [foodname], (err, results) => {
        if (err) {
            return res.status(500).send('An error occurred while fetching food details');
        }

        if (results.length > 0) {
            const food = results[0];
            const isFavorite = food.favourite === 1;

            // Fetch comments
            const sqlGetComments = 'SELECT comments FROM foods WHERE name = ?';
            db.query(sqlGetComments, [foodname], (err, commentsResults) => {
                if (err) {
                    return res.status(500).send('An error occurred while fetching comments');
                }

                let commentsHtml = '';
                if (commentsResults.length > 0 && commentsResults[0].comments) {
                    const comments = JSON.parse(commentsResults[0].comments);
                    // Generate HTML for each comment individually
                    commentsHtml = comments.map(c => `<div><strong>${c.username}:</strong> ${c.comment}</div>`).join('');
                } else {
                    commentsHtml = '<div>No comments yet</div>';
                }

                // Render the details.html with food data
                let foodDetailsHtml = `
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>FoodLore - Food Details</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
                    <style>
                        body {
                            background-image: url('images/background.jpg');
                            background-size: cover;
                            background-position: center;
                            background-repeat: no-repeat;
                            background-attachment: fixed;
                            color: #fff;
                        }
                        .food-card {
                            border: none;
                            border-radius: 10px;
                            overflow: hidden;
                            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                            margin-bottom: 100px;
                            padding-bottom: 30px;
                        }
                        .food-image {
                            width: 60%;
                            height: auto;
                            border-bottom: 5px solid #f8f9fa;
                            margin: 0 auto;
                            display: block;
                        }
                        .food-details {
                            padding: 20px;
                        }
                        .food-name {
                            font-size: 24px;
                            font-weight: bold;
                            margin-bottom: 10px;
                        }
                        .favorite-button {
                            font-size: 24px;
                            color: #e74c3c;
                            cursor: pointer;
                            border: none;
                            background-color: transparent;
                        }
                        .food-description {
                            font-size: 16px;
                            margin-bottom: 20px;
                            text-align: justify;
                        }
                    </style>
                </head>
                <body>
                    <div class="container mt-5">
                        <div class="row justify-content-center">
                            <div class="col-md-10">
                                <div class="card food-card">
                                    <img src="${food.imageUrl}" alt="Food Image" class="food-image">
                                    <div class="food-details">
                                        <div class="food-name d-flex justify-content-between align-items-center">
                                            <div>${food.name}</div>
                                            <button class="favorite-button" id="favorite-button" style="border: none; background: none;">
                                                <i class="bi ${isFavorite ? 'bi-heart-fill' : 'bi-heart'}"></i>
                                            </button>
                                        </div>
                                        <div class="food-description">${food.description}</div>
                                    </div>

                                    <div class="comments-section mt-4">
                                        <h5>Comments:</h5>
                                        <div class="comments-container">${commentsHtml}</div>
                                        <form id="commentForm" class="mt-3">
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="comment" placeholder="Add a comment" required>
                                                <button class="btn btn-primary" type="submit">Submit</button>
                                            </div>
                                            <input type="hidden" id="foodname" value="${food.name}">
                                            <input type="hidden" id="username" value="${username}">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script>
                        // Favorite button functionality
                        document.getElementById('favorite-button').addEventListener('click', function() {
                            const icon = this.querySelector('i');
                            let newFavouriteValue;

                            if (icon.classList.contains('bi-heart')) {
                                icon.classList.remove('bi-heart');
                                icon.classList.add('bi-heart-fill');
                                newFavouriteValue = 1;
                            } else {
                                icon.classList.remove('bi-heart-fill');
                                icon.classList.add('bi-heart');
                                newFavouriteValue = 0;
                            }

                            fetch('/updateFavorite', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({ foodname: '${food.name}', favourite: newFavouriteValue })
                            }).then(response => {
                                if (response.ok) {
                                    console.log('Favorite updated');
                                } else {
                                    console.error('Failed to update favorite');
                                }
                            });
                        });

                        // Comment submission
                        document.getElementById('commentForm').addEventListener('submit', function(event) {
                            event.preventDefault();
                            const commentInput = document.getElementById('comment');
                            const foodname = document.getElementById('foodname').value;
                            const username = document.getElementById('username').value;

                            fetch('/addComment', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({
                                    foodname: foodname,
                                    username: username,
                                    comment: commentInput.value
                                })
                            }).then(response => {
                                if (response.ok) {
                                    commentInput.value = '';
                                    loadComments(foodname); // Reload comments after submission
                                } else {
                                    console.error('Failed to add comment');
                                }
                            });
                        });

                        function loadComments(foodname) {
                            fetch('/getComments?foodname=' + encodeURIComponent(foodname))
                                .then(response => response.text())
                                .then(data => {
                                    let text = "";
                                    let comments = data.split("|");
                                    comments.forEach(myFunction);
                                    document.querySelector('.comments-container').innerHTML = text;
                                    function myFunction(item, index) {
                                        text += item + "<br>"; 
                                    }
                                    
                                });
                        }
                        // Load comments on page load
                        loadComments('${food.name}');
                    </script>
                </body>
                </html>`;

                res.send(foodDetailsHtml);
            });
        } else {
            res.status(404).send('Food not found');
        }
    });
});


app.post('/updateFavorite', (req, res) => {
    const { foodname, favourite } = req.body;

    if (!foodname || favourite === undefined) {
        return res.status(400).send('Food name and favorite status are required');
    }

    const sql = 'UPDATE foods SET favourite = ? WHERE name = ?';
    db.query(sql, [favourite, foodname], (err, result) => {
        if (err) {
            return res.status(500).send('An error occurred while updating favorite status');
        }

        res.status(200).send('Favorite updated successfully');
    });
});

app.listen(3000, () => {
    console.log('Server running on port 3000');
});
