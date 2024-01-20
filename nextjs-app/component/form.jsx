import { useState } from "react";

export default function Form() {
    const [file, setFile] = useState(null);

    const submitHandler = (e) => {
        e.preventDefault();
        // Handle the file submission here
        console.log(file); // For example, log the file to the console

        //!!! if file is a CSV
        if(file){
            const formData = new FormData();
            formData.append('file', file)

            fetch('http://localhost:8080/upload', {
                method: 'POST',
                body: formData,
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("Response was not OK")
            })
            .then(data => console.log(data))
            .catch(error => console.error('Error: ', error));
        } else {
            console.log("No file selected. ")
        }
    };

    return (
        <main>
            <form onSubmit={submitHandler}>
                <input
                    type="file"
                    name="file"
                    onChange={(e) => setFile(e.target.files[0])}
                />
                <input type="submit" value="Upload" />
            </form>
        </main>
    );
}
