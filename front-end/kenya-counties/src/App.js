import logo from './logo.svg'
import './App.css'
import counties from './data'
import List from './List'

function App() {
  return (
    <main>
      <section className='container'>
        <List countiesData={counties} />
      </section>
    </main>
  )
}

export default App
