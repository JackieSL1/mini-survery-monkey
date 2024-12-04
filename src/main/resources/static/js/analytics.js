export function generateHistogram(values, canvasId){

    const numbers = values;

    const binSize = 2;
    const min = Math.min(...numbers);
    const max = Math.max(...numbers);
    const bins = [];

    for (let i = min; i <= max; i += binSize) {
        bins.push(i);
    }

    const binCounts = new Array(bins.length).fill(0);
    numbers.forEach(num => {
        const binIndex = Math.floor((num - min) / binSize);
        binCounts[binIndex]++;
    });

    const ctx = document.getElementById(canvasId).getContext('2d');
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
                    title: { display: true, text: 'Responses' }
                },
                y: {
                    title: { display: true, text: 'Frequency' },
                    beginAtZero: true,
                    ticks: {
                        callback: function(value){
                            return value % 1 === 0 ? value : '';
                        }
                    }
                }
            }
        }
    });
}
