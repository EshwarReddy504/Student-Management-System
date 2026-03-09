import React, { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

function App() {

  const [students, setStudents] = useState([]);
  const [name, setName] = useState("");
  const [course, setCourse] = useState("");
  const [editId, setEditId] = useState(null);

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = () => {
    axios.get("http://localhost:9090/students")
      .then(res => setStudents(res.data));
  };

  // ADD or UPDATE
  const saveStudent = () => {

    if(editId === null){

      axios.post("http://localhost:9090/students", {
        name:name,
        course:course
      }).then(() => {
        fetchStudents();
        setName("");
        setCourse("");
      });

    }else{

      axios.put(`http://localhost:9090/students/${editId}`, {
        name:name,
        course:course
      }).then(()=>{
        fetchStudents();
        setEditId(null);
        setName("");
        setCourse("");
      });

    }

  };

  // DELETE
  const deleteStudent = (id) => {
    axios.delete(`http://localhost:9090/students/${id}`)
      .then(()=>fetchStudents());
  };

  // EDIT
  const editStudent = (student) => {
    setName(student.name);
    setCourse(student.course);
    setEditId(student.id);
  };

  return (

    <div className="container">

      <h1>Student Management System</h1>

      <h3>Add / Update Student</h3>

      <input
        placeholder="Student Name"
        value={name}
        onChange={(e)=>setName(e.target.value)}
      />

      <input
        placeholder="Course"
        value={course}
        onChange={(e)=>setCourse(e.target.value)}
      />

      <button onClick={saveStudent}>
        {editId ? "Update" : "Add"}
      </button>

      <h3>Students List</h3>

      <ul>
        {students.map((s)=>(
          <li key={s.id}>

            {s.name} - {s.course}

            <button
              style={{marginLeft:"10px",background:"green"}}
              onClick={()=>editStudent(s)}
            >
              Edit
            </button>

            <button
              style={{marginLeft:"5px",background:"red"}}
              onClick={()=>deleteStudent(s.id)}
            >
              Delete
            </button>

          </li>
        ))}
      </ul>

    </div>

  );
}

export default App;