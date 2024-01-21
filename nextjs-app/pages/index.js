import Head from 'next/head';
import styles from '../styles/Home.module.css';
import Form from '../component/form';
import { DataProvider } from '../utils/DataContext';
import Navbar from '../component/navbar';

export default function Home() {
  return (
    <div className={styles.container}>
      <Head>
        <title>Tires</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <DataProvider>
        <Navbar/>
        <Form></Form>
      </DataProvider>
    </div>
  );
}
