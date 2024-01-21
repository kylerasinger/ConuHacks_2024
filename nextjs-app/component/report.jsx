import { useEffect, useState } from 'react';
import styles from '../styles/Home.module.css';


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
        }, 15000); // Poll every 5000 milliseconds (5 seconds)

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
            <div className={styles.infoContainer}>
                <div className={styles.greenGradientBox}>
                    <h3>Total Actual Vehicles</h3>
                    {scheduleData.totalActualVehicles}
                </div>
                <div className={styles.redGradientBox}>
                    <h3>Total Missed Vehicles</h3>
                    {scheduleData.totalMissedVehicles}
                </div>
            </div>
            <div className={styles.infoContainer}>
                <div className={styles.greenGradientBox}>
                    <h3>Total Actual Revenue</h3> 
                    ${scheduleData.totalActualRevenue}
                </div>
                <div className={styles.redGradientBox}>
                    <h3>Total Missed Revenue</h3>
                    ${scheduleData.totalMissedRevenue}
                </div>
            </div>
        </div>
    );
};
