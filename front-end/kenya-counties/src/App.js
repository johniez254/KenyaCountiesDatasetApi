import React from 'react'
import logo from './logo.svg'
import data from './data'
import List from './List'
import { useState } from 'react'

function App() {
  const [loading, setLoading] = useState(false)
  const [counties, setCounties] = useState(data)

  if (loading) {
    return (
      <section className='section loading'>
        <h1>loading...</h1>
      </section>
    )
  }

  return (
    <section className='section'>
      <div className='title'>
        <h2>counties {counties.length}</h2>
        <div className='underline'></div>
      </div>
      <List countiesData={counties} />
    </section>
  )
}

export default App
