import { useState } from "react";
import styles from "../styles/Home.module.css";
import Report from "./report";

export default function Form() {
    const [file, setFile] = useState(null);
    const [fileName, setFileName] = useState('No file selected');
    const [processStarted, setProcessStarted] = useState(false);



    const submitHandler = (e) => {
        e.preventDefault();
        // Handle the file submission here
        console.log(file); // For example, log the file to the console

        //!!! if file is a CSV
        if(file){
            const formData = new FormData();
            formData.append('datafile', file)

            fetch('http://localhost:8080/api/v1/vehicle/upload', {
                method: 'POST',
                body: formData,
            })
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error("Response was not OK")
            })
            .then(data => console.log(data))
            .catch(error => console.error('Error: ', error));
        } else {
            console.log("No file selected. ")
        }
    };

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
        setFileName(e.target.files[0].name); // Update the file name
    };

    const handleProcessStarted = (e) => {
        console.log("Process started. ")
        setProcessStarted(true);
    };

    return (
        <main>
            <form onSubmit={submitHandler} className={styles.formContainer}>
                <label className={styles.customFileButton}>
                    {fileName} {/* Display the selected file name */}
                    <input
                        type="file"
                        name="file"
                        className={styles.customFileInput}
                        onChange={handleFileChange}
                    />
                </label>
                <input
                    type="submit"
                    value="Upload"
                    className={styles.greenButton}
                    onClick={handleProcessStarted}
                />
            </form>
            {processStarted && <Report/>}
            
        </main>
    );
}