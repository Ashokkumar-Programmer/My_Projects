$(function() {

  $('.js-check-all').on('click', function() {

  	if ( $(this).prop('checked') ) {
	  	$('th input[type="checkbox"]').each(function() {
	  		$(this).prop('checked', true);
	  	})
  	} else {
  		$('th input[type="checkbox"]').each(function() {
	  		$(this).prop('checked', false);
	  	})
  	}
  });
});

$(document).ready(function() {
    // Fetch all foods and display them in the table
    fetch('/getFoods')
        .then(response => response.json())
        .then(data => {
            let tableBody = $('#foodTable tbody');
            tableBody.empty(); // Clear any existing rows

            data.forEach((food, index) => {
                let row = `
                    <tr>
                        <td>${index + 1}</td>
                        <td><img src="${food.imageUrl}" alt="${food.name}" style="width: 100px;"></td>
                        <td>${food.name}</td>
                        <td contenteditable="true" data-id="${food.id}" class="editable">${food.description}</td>
                        <td>
                            <button class="btn btn-primary update-btn" data-id="${food.id}">Update</button>
                            <button class="btn btn-danger delete-btn" data-id="${food.id}">Delete</button>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });

            // Update event
            $('.update-btn').click(function() {
                let id = $(this).data('id');
                let newDescription = $(this).closest('tr').find('.editable').text();
                updateFood(id, newDescription);
            });

            // Delete event
            $('.delete-btn').click(function() {
                let id = $(this).data('id');
                deleteFood(id);
            });
        })
        .catch(error => console.error('Error fetching food data:', error));
});

function updateFood(id, newDescription) {
    $.ajax({
        url: `/updateFood/${id}`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ description: newDescription }),
        success: function(response) {
            alert('Food description updated successfully');
        },
        error: function(error) {
            alert('An error occurred while updating the food description');
        }
    });
}

function deleteFood(id) {
    $.ajax({
        url: `/deleteFood/${id}`,
        method: 'DELETE',
        success: function(response) {
            alert('Food deleted successfully');
            location.reload(); // Reload the page to see changes
        },
        error: function(error) {
            alert('An error occurred while deleting the food');
        }
    });
}
