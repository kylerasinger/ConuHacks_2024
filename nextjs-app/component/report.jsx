import { useEffect, useState } from 'react';

export default function VehicleSchedule() {
    const [scheduleData, setScheduleData] = useState(null);

    useEffect(() => {
        const interval = setInterval(() => {
            fetch('http://localhost:8080/api/v1/vehicle/create-schedule')
                .then(response => response.json())
                .then(data => {
                    if (dataIsReady(data)) {
                        setScheduleData(data);
                        clearInterval(interval);
                    }
                })
                .catch(error => console.error('Error fetching data: ', error));
        }, 5000); // Poll every 5000 milliseconds (5 seconds)

        return () => clearInterval(interval);
    }, []);

    const dataIsReady = (data) => {
        // Check if all the properties are not null or undefined
        return data &&
               data.totalActualVehicles != null &&
               data.totalMissedVehicles != null &&
               data.totalActualRevenue != null &&
               data.totalMissedRevenue != null;
    };

    if (!scheduleData) {
        return <div>Loading data...</div>;
    }

    return (
        <div>
            <div>Total Actual Vehicles: {scheduleData.totalActualVehicles}</div>
            <div>Total Missed Vehicles: {scheduleData.totalMissedVehicles}</div>
            <div>Total Actual Revenue: ${scheduleData.totalActualRevenue.toFixed(2)}</div>
            <div>Total Missed Revenue: ${scheduleData.totalMissedRevenue.toFixed(2)}</div>
        </div>
    );
};
