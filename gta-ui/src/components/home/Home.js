import { useAuth } from '../context/AuthContext';
import React, { useEffect, useState } from 'react';
import { gtaApi } from '../misc/GtaApi'
import { FaSpinner, FaCheckCircle, FaTimesCircle, FaExclamationCircle, FaHourglassHalf, FaBan, FaSearch, FaExclamationTriangle } from "react-icons/fa";
import StatusLegendModal from './StatusLegendModal'; // Import the modal component
import {  Form, Button, Container, Table, Dropdown, Segment } from 'semantic-ui-react';

import Swal from 'sweetalert2';


const Home = () => {
  const { getUser } = useAuth();  // Access the getUser function from context

  const user = getUser();  // Get the user object

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [gtaHistoryCourses, setGtaHistoryCourses] = useState([]);


  const hasData = (data) => data && data.length > 0;

  const courseOptions = [
    { key: 'CYSE 101', text: 'CYSE 101: Introduction to Cyber Security Engineering', value: 'CYSE 101' },
    { key: 'CYSE 130', text: 'CYSE 130: Introduction to Computing for Digital Systems Engineering', value: 'CYSE 130' },
    { key: 'CYSE 211', text: 'CYSE 211: Operating Systems and Lab', value: 'CYSE 211' },
    { key: 'CYSE 230', text: 'CYSE 230: Computer Networking', value: 'CYSE 230' },
    { key: 'CYSE 304', text: 'CYSE 304: Cyber Security in Logic Design and Digital Systems', value: 'CYSE 304' },
    { key: 'CYSE 395', text: 'CYSE 395: Cyber Security Engineering Internship', value: 'CYSE 395' },
    { key: 'CYSE 411', text: 'CYSE 411: Secure Software Engineering', value: 'CYSE 411' },
    { key: 'CYSE 421', text: 'CYSE 421: Industrial Control Systems Security', value: 'CYSE 421' },
    { key: 'CYSE 424', text: 'CYSE 424: Embedded and Real Time Systems', value: 'CYSE 424' },
    { key: 'CYSE 425', text: 'CYSE 425: Secure RF Communications', value: 'CYSE 425' },
    { key: 'CYSE 430', text: 'CYSE 430: Critical Infrastructure Protection', value: 'CYSE 430' },
    { key: 'CYSE 445', text: 'CYSE 445: System Security and Resilience', value: 'CYSE 445' },
    { key: 'CYSE 450', text: 'CYSE 450: Cyber Vulnerability Lab', value: 'CYSE 450' },
    { key: 'CYSE 460', text: 'CYSE 460: Power Systems and Smart Grid Security', value: 'CYSE 460' },
    { key: 'CYSE 461', text: 'CYSE 461: Power Grid Security', value: 'CYSE 461' },
    { key: 'CYSE 462', text: 'CYSE 462: Mobile Devices and Network Security', value: 'CYSE 462' },
    { key: 'CYSE 465', text: 'CYSE 465: Transportation Systems Design', value: 'CYSE 465' },
    { key: 'CYSE 467', text: 'CYSE 467: GPS Security', value: 'CYSE 467' },
    { key: 'CYSE 470', text: 'CYSE 470: Human Factors and Cyber Security Engineering', value: 'CYSE 470' },
    { key: 'CYSE 476', text: 'CYSE 476: Cryptography Fundamentals', value: 'CYSE 476' },
    { key: 'CYSE 477', text: 'CYSE 477: Intrusion Detection', value: 'CYSE 477' },
    { key: 'CYSE 478', text: 'CYSE 478: Cyber Security Audit and Compliance', value: 'CYSE 478' },
    { key: 'CYSE 479', text: 'CYSE 479: Methods of User Authentication', value: 'CYSE 479' },
    { key: 'CYSE 480', text: 'CYSE 480: Reverse Software Engineering', value: 'CYSE 480' },
    { key: 'CYSE 491', text: 'CYSE 491: Engineering Senior Seminar', value: 'CYSE 491' },
    { key: 'CYSE 492', text: 'CYSE 492: Senior Advanced Design Project I', value: 'CYSE 492' },
    { key: 'CYSE 493', text: 'CYSE 493: Senior Advanced Design Project II', value: 'CYSE 493' },
    { key: 'CYSE 498', text: 'CYSE 498: Independent Study in Cyber Security Engineering', value: 'CYSE 498' },
    { key: 'CYSE 499', text: 'CYSE 499: Special Topics in Cyber Security Engineering', value: 'CYSE 499' },
    { key: 'CYSE 521', text: 'CYSE 521: Industrial Control Systems Security', value: 'CYSE 521' },
    { key: 'CYSE 550', text: 'CYSE 550: Cyber Security Engineering Fundamentals', value: 'CYSE 550' },
    { key: 'CYSE 570', text: 'CYSE 570: Fundamentals of Operating Systems', value: 'CYSE 570' },
    { key: 'CYSE 580', text: 'CYSE 580: Hardware and Cyber Physical Systems', value: 'CYSE 580' },
    { key: 'CYSE 587', text: 'CYSE 587: Cyber Security Systems Engineering', value: 'CYSE 587' },
    { key: 'CYSE 610', text: 'CYSE 610: Networks and Cyber Security', value: 'CYSE 610' },
    { key: 'CYSE 630', text: 'CYSE 630: Cyber Risk Analysis and Advanced Tools', value: 'CYSE 630' },
    { key: 'CYSE 640', text: 'CYSE 640: Wireless Network Security', value: 'CYSE 640' },
    { key: 'CYSE 650', text: 'CYSE 650: Topics in Cyber Security Engineering', value: 'CYSE 650' },
    { key: 'CYSE 670', text: 'CYSE 670: Secure Design of Connected and Automated Vehicles', value: 'CYSE 670' },
    { key: 'CYSE 680', text: 'CYSE 680: Advanced Manufacturing Automation Security', value: 'CYSE 680' },
    { key: 'CYSE 681', text: 'CYSE 681: Secure Energy Efficient Supply Chains', value: 'CYSE 681' },
    { key: 'CYSE 682', text: 'CYSE 682: Formal Methods for Cyber Physical Systems Security', value: 'CYSE 682' },
    { key: 'CYSE 683', text: 'CYSE 683: Reverse Engineering Industrial Automation', value: 'CYSE 683' },
    { key: 'CYSE 685', text: 'CYSE 685: Unmanned Aerial Systems Security', value: 'CYSE 685' },
    { key: 'CYSE 689', text: 'CYSE 689: Artificial Intelligence Methods for Cybersecurity', value: 'CYSE 689' },
    { key: 'CYSE 690', text: 'CYSE 690: Cyber Security Engineering Capstone Project', value: 'CYSE 690' },
    { key: 'CYSE 698', text: 'CYSE 698: Independent Study and Research', value: 'CYSE 698' },
    { key: 'CYSE 700', text: 'CYSE 700: Research Methodology and Pedagogy in Cyber Security Engineering', value: 'CYSE 700' },
    { key: 'CYSE 701', text: 'CYSE 701: Advanced Cyber Security Systems Engineering', value: 'CYSE 701' },
    { key: 'CYSE 720', text: 'CYSE 720: Security of Autonomous Systems', value: 'CYSE 720' },
    { key: 'CYSE 750', text: 'CYSE 750: Secure Software Development Lifecycle', value: 'CYSE 750' },
    { key: 'CYSE 799', text: 'CYSE 799: Special Topics in Cyber Security Engineering', value: 'CYSE 799' },
  ];

 // Period dropdown options
 const semesterOptions = [
  { key: 'summer', text: 'SUMMER', value: 'SUMMER' },
  { key: 'spring', text: 'SPRING', value: 'SPRING' },
  { key: 'fall', text: 'FALL', value: 'FALL' },
];

  const [dataLoaded, setDataLoaded] = useState(false);

  const getStatusIcon = (status) => {
    switch (status.toLowerCase()) {
      case 'inprocessing':
        return <FaSpinner title="In Processing" style={{ color: '#ffc107' }} />;
      case 'approved':
        return <FaCheckCircle title="Approved" style={{ color: '#28a745' }} />;
      case 'deny':
        return <FaTimesCircle title="Deny" style={{ color: '#dc3545' }} />;
      case 'incompleted':
        return <FaExclamationTriangle title="Incomplete" style={{ color: '#fd7e14' }} />;
      case 'notstarted':
        return <FaHourglassHalf title="Not Started" style={{ color: '#6c757d' }} />;
      case 'nosubmited':
        return <FaBan title="Not Submitted" style={{ color: '#6c757d' }} />;
      case 'checked':
        return <FaSearch title="Checked" style={{ color: '#007bff' }} />;
      case 'error':
        return <FaExclamationCircle title="Error" style={{ color: '#dc3545' }} />;
      default:
        return <FaBan title="Unknown Status" style={{ color: '#6c757d' }} />;
    }
  };

  useEffect(() => {
    if (user && !dataLoaded) {
      const token = localStorage.getItem("authToken");

      if (!token) return;

      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };

      console.log("verificar",user)

     gtaApi
        .loadGTAApplicationInfo(user, config)
        .then(response => {
          const historyCourses = response.data.application_info?.gtaHistoryCourses || [];
          setGtaHistoryCourses(historyCourses);
          setData(response.data);
          setLoading(false);
          setDataLoaded(true); // Set the flag to prevent future calls
          
        

        })
        .catch(error => {
          console.error("Error fetching data:", error);
          setLoading(false);
        });
        
    }
  }, [user, dataLoaded]);

  const handleAddCourse = () => {
     setGtaHistoryCourses([...gtaHistoryCourses, { cyseId: "", semester: "", year: "" }]);
  };

  // Delete a course
  const handleDeleteCourse = (index) => {
    const updatedCourses = gtaHistoryCourses.filter((_, idx) => idx !== index);
    setGtaHistoryCourses(updatedCourses);
  };

  // Update a course
   const handleCourseChange = (index, field, value) => {
     const updatedCourses = gtaHistoryCourses.map((course, idx) =>
       idx === index ? { ...course, [field]: value } : course
     );
     setGtaHistoryCourses(updatedCourses);
   };


  const handleSaveChanges = async () =>  {

    const token = localStorage.getItem("authToken");
    console.log("Token:", token); // Debug line

        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };

        console.log("passei aqui33333"); // Debug line


        let response = await gtaApi.updateGTAHistoryCourses(user, gtaHistoryCourses, config);

        console.log("passei aqui 525252", response)

        if (response?.status === 401) {
          console.log('passei aqui')
          await gtaApi.renewToken(); // Call your token renewal function here
          config.headers["Authorization"] = `Bearer ${localStorage.getItem("authToken")}`;

          response = await  gtaApi.updateGTAHistoryCourses(user, gtaHistoryCourses, config);
        }

        if (response?.status === 200) {
          Swal.fire({
            title: 'Success',
            text: 'Application submitted successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });

        
        } 
      
  };


  if (loading) {
    return <p>Loading data...</p>;
  }

  if (!data || !data.application_info) {
    return <p>No application data available.</p>;
  }


  // If user data is available, display the name, else display a default message
  return (
    <Container className='gmu-theme'>
      <div className="content-container">
        {user && user.data ? (
          <h2>Welcome, {user.data.email}!</h2>  // Display the user's name
        ) : (
          <h2>Welcome, Guest!</h2>  // Fallback if no user is logged in
        )}

        <h4>Application Information</h4>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Username</th>
              <th>International Student</th>
              <th>Student was GTA</th>
              <th>Application Submission</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{data.application_info.username || 'N/A'}</td>
              <td>{data.application_info.isInternationalStudent ? "Yes" : "No"}</td>
              <td>{data.application_info.wasGTA ? "Yes" : "No"}</td>
              <td>{data.process_info.ApplicationSubmission || 'N/A'}</td>
            </tr>
          </tbody>
        </table>

        <h4>Process Information</h4>
        <table className="styled-table">
          <tbody>
            {/* Line 1 */}
            <tr>
              <th style={{ width: '20%' }}>CYSE Admin Office Decision</th>
              <td>{getStatusIcon(data.process_info.CYSEAdminOfficeDecision || 'nosubmited')}</td>
              <th style={{ width: '20%' }}>CYSE Admin Comments</th>
              <td
                style={{
                  width: '30%',
                  whiteSpace: 'pre-wrap',
                  wordBreak: 'break-word',
                  maxHeight: '4.5em',  // Limit height for 3 lines of text
                  overflow: 'hidden',
                  textOverflow: 'ellipsis'
                }}
                title={data.process_info.CYSEAdminComments || 'N/A'} // Tooltip for full text on hover
              >
                {data.process_info.CYSEAdminComments || 'N/A'}
              </td>
            </tr>

            {/* Line 2 */}
            <tr>
              <th>CYSE Chair Decision</th>
              <td>{getStatusIcon(data.process_info.CYSEChairDecision || 'nosubmited')}</td>
              <th>CYSE Chair Comments</th>
              <td>{data.process_info.CYSEChairComments || 'N/A'}</td>
            </tr>

            {/* Line 3 */}
            <tr>
              <th>Course Allocated</th>
              <td>{data.process_info.CourseAllocated || 'N/A'}</td>
              <th>Contract Signed</th>
              <td>{data.process_info.ContractSigned ? "Yes" : "No"}</td>
            </tr>

            {/* Line 4 */}
            <tr>
              <th>CELTD Status</th>
              <td>{getStatusIcon(data.process_info.CELTDSTATUS || 'notstarted')}</td>
              <th>CV Status</th>
              <td>{getStatusIcon(data.process_info.CVSTATUS || 'notstarted')}</td>
            </tr>

            <tr>
              <th>Transcript Status</th>
              <td>{getStatusIcon(data.process_info.TRASCRIPTSTATUS || 'notstarted')}</td>
              <th>TOEFL Status</th>
              <td>{getStatusIcon(data.process_info.TOEFLSTATUS || 'notstarted')}</td>
            </tr>

            <tr>
              <th>Video Status</th>
              <td>{getStatusIcon(data.process_info.VIDEOSTATUS || 'notstarted')}</td>
            </tr>

            {/* Line 5 */}
            <tr>
              <th>Last Update</th>
              <td colSpan="5">{data.process_info.LastUpdate || 'N/A'}</td>
            </tr>
          </tbody>
        </table>

        <h4>GTA History Courses</h4>
        {

        <>
        <Segment>
        <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>CYSE-ID</Table.HeaderCell>
                    <Table.HeaderCell>Semester</Table.HeaderCell>
                    <Table.HeaderCell>Year</Table.HeaderCell>
                    <Table.HeaderCell></Table.HeaderCell>
                    
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {!hasData(gtaHistoryCourses)&&(
                    <Table.Row>
                      <div>No GTA history courses</div>
                      </Table.Row>
                  )}

                  
                  {gtaHistoryCourses.map((entry, index) => (
                    <Table.Row key={index}>
                      <Table.Cell>
                        <Dropdown
                          selection
                          options={courseOptions}
                          value={entry.cyseId}
                          onChange={(e, { value }) => handleCourseChange(index, 'cyseId', value)}
                          placeholder='Select Course'
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Dropdown
                          selection
                          options={semesterOptions}
                          value={entry.semester}
                          onChange={(e, { value }) => handleCourseChange(index, 'semester', value)}
                          placeholder='Select Semester'
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Form.Input
                          value={entry.year}
                          onChange={(e) => handleCourseChange(index, 'year', e.target.value)}
                          placeholder='Enter Year'
                        />
                      </Table.Cell>


                      <Table.Cell>
                      <Button onClick={()=> handleDeleteCourse(index)} primary>Delete</Button>
                      </Table.Cell>
                    
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>

              <Button onClick={handleAddCourse} primary>Add Row</Button>
              </Segment>

              <Segment>
              <Button onClick={handleSaveChanges} primary>Save Changes</Button>
              </Segment>
        </>
        
        
        
        }
        {/* Status Legend Modal */}
        <StatusLegendModal getStatusIcon={getStatusIcon} />

      </div>
    </Container>

  );
};

export default Home;