// JS functions for questions.

// Shows the multiple-choice form.
function showMultipleChoiceForm() {
    document.getElementById('multipleChoiceForm').style.display = 'block';
}

// Adds an answer choice.
function addChoice() {
    const container = document.getElementById('answerChoices');
    const choice = document.createElement('p');
    choice.innerHTML = `
            <input type="text" name="options" placeholder="Enter an answer choice" required/>
            <button type="button" onclick="removeChoice(this)">-</button>
        `;
    container.appendChild(choice);
}

// Removes a specific answer choice input field.
function removeChoice(button) {
    button.parentElement.remove();
}

function showScaleForm() {
    document.getElementById("scaleForm").style.display = "block";
    document.getElementById("multipleChoiceForm").style.display = "none";
}
