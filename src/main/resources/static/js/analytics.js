const numbers = [1,2,3,4,5];

const binSize = 2; // Define bin size
const min = Math.min(...numbers); // Find minimum value
const max = Math.max(...numbers); // Find maximum value
const bins = [];

for (let i = min; i <= max; i += binSize) {
    bins.push(i);
}

const binCounts = new Array(bins.length).fill(0);
numbers.forEach(num => {
    const binIndex = Math.floor((num - min) / binSize);
    binCounts[binIndex]++;
});

const ctx = document.getElementById('histogram').getContext('2d');
const histogram = new Chart(ctx, {
    type: 'bar', // Bar chart for histogram
    data: {
        labels: bins.map((bin, i) => `${bin} - ${bin + binSize - 1}`), // Bin labels
        datasets: [{
            label: 'Frequency',
            data: binCounts,
            backgroundColor: 'rgba(75,192,102,0.5)',
            borderColor: 'rgb(93,192,75)',
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            x: {
                title: { display: true, text: 'Bins' }
            },
            y: {
                title: { display: true, text: 'Frequency' },
                beginAtZero: true
            }
        }
    }
});
