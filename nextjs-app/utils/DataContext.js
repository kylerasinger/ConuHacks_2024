import React, { createContext, useState, useEffect } from 'react';

export const DataContext = createContext();

//this is no good, when the page is rendered it makes a fetch request, which 
//is empty as server doesnt have the CSV yet. 

export const DataProvider = ({ children }) => {
    const [data, setData] = useState(null);

    useEffect(() => {
        fetch("http://localhost:3000/api/stats")
            .then(response => response.json())
            .then(data => setData(data))
            .then(console.log(data))
            .catch(error => console.error('Error:', error));
    }, []); 

    return (
        <DataContext.Provider value={data}>
            {children}
        </DataContext.Provider>
    )
}