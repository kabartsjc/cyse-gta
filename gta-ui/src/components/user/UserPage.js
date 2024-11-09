import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { gtaApi } from '../misc/GtaApi'

import { Container, Form, Button, Segment, Table, Dropdown } from 'semantic-ui-react';
import Swal from 'sweetalert2';



function UserPage() {
  const Auth = useAuth();
  const user = Auth.getUser();
  const isUser = user.data.rol[0] === 'USER';

  const [cvFile, setCVFile] = useState(null);
  const [introGTAVideo, setIntroGTAVideo] = useState(null);

  const [isInternationalStudentCB, setIsInternationalStudentCB] = useState(false);
  const [celtdCertFile, setCeltdCertFile] = useState(null);
  const [toeflScoreFile, setToeflScoreFile] = useState(null);

  const [wasGTACB, setWasGTACB] = useState(false);
  const [gtaCourseHistory, setGtaCourseHistory] = useState([{ cyseId: '', semester: '', year: '' }]);
  const [studentHistory, setStudentHistory] = useState([{ cyseId: '', semester: '', year: '', grade: '' }]);

  const [selectedCourses, setSelectedCourses] = useState([]);

  const semesterOptions = [
    { key: 'summer', text: 'SUMMER', value: 'SUMMER' },
    { key: 'spring', text: 'SPRING', value: 'SPRING' },
    { key: 'fall', text: 'FALL', value: 'FALL' },
  ];

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

  const gradeOptions = [
    { key: 'A', text: 'A', value: 'A' },
    { key: 'B', text: 'B', value: 'B' },
    { key: 'C', text: 'C', value: 'C' },
    { key: 'D', text: 'D', value: 'D' },
    { key: 'F', text: 'F', value: 'F' },
  ];


  const handleIntStdCheckboxChange = (e, { checked }) => {
    setIsInternationalStudentCB(checked);
  };

  const handleGTAHistCheckboxChange = (e, { checked }) => {
    setWasGTACB(checked);
  };

  const handleFileChange = (e, setFileFunction) => {
    setFileFunction(e.target.files[0]);
  };

  const handleGtaHistoryChange = (index, field, value) => {
    const updatedHistory = [...gtaCourseHistory];
    updatedHistory[index][field] = value;
    setGtaCourseHistory(updatedHistory);
  };

  const handleStudentHistoryChange = (index, field, value) => {
    const updatedHistory = [...studentHistory];
    updatedHistory[index][field] = value;
    setStudentHistory(updatedHistory);
  };

  const addGtaHistoryRow = () => {
    setGtaCourseHistory([...gtaCourseHistory, { cyseId: '', semester: '', year: '' }]);
  };

  const addStudentHistoryRow = () => {
    setStudentHistory([...studentHistory, { cyseId: '', semester: '', year: '', grade: '' }]);
  };

  //  const application = { username,cvFile, introGTAVideo,  isInternationalStudent, isInternationalStudent,wasGTA,studentHistoryCB,}


  const handleOrderChange = (index, value) => {
    const updatedCourses = [...selectedCourses];
    updatedCourses[index].order = value;
    setSelectedCourses(updatedCourses);
  };

  const handleSubmitApplication = async () => {
    try {
      // Create a FormData object to hold the files
      const formData = new FormData();

      let form_error = false

      let form_error_descript = ""


      if (cvFile) {
        formData.append('cvFile', cvFile);
      } else {
        form_error = true
        form_error_descript += 'CV File was not submitted!\n';
      }


      if (introGTAVideo) {
        formData.append('introGTAVideo', introGTAVideo);
      } else {
        form_error = true
        form_error_descript += 'Introduction Video was not submitted!\n';
      }


      // Create a JSON object for the application data
      const applicationData = {
        isInternationalStudent: isInternationalStudentCB,
        wasGTACB: wasGTACB,
      };

      // Only add student history if the student was a previous GTA
      if (wasGTACB) {

        if (gtaCourseHistory.length !== 0) {
          applicationData.gtaCourseHistory = gtaCourseHistory; // This contains previous course history with grades
        } else {
          form_error = true
          form_error_descript += 'Your GTA history was not provided!\n';
        }
      }

      // CHECK IF THE STUDENT FILL ITS GRADE
      if (!studentHistory || studentHistory.length === 0) {
        form_error = true
        form_error_descript += 'Your course grade history was not provided !\n';
      } else {
        applicationData.studentHistory = studentHistory; // This contains previous course history with grades

      }

      if (!selectedCourses || selectedCourses.length === 0) {
        form_error = true
        form_error_descript += 'You did not select the courses that you want to be a GTA!\n';
      } else {
        applicationData.selectedCourses = selectedCourses; // This contains previous course history with grades
      }

      // Include additional files only for international students
      if (isInternationalStudentCB) {
        if (celtdCertFile) {
          formData.append('celtdCertFile', celtdCertFile);
        } else {
          form_error = true
          form_error_descript += 'You did not provide the CELTD certificate!\n';
        }
        if (toeflScoreFile) {
          formData.append('toeflScoreFile', toeflScoreFile);
        } else {
          form_error = true
          form_error_descript += 'You did not provide your TOEFL report!\n';
        }
      }

      if (!form_error) {
        // Send the application data to the server

        formData.append('application_data', JSON.stringify(applicationData)); // Append application data


        const token = localStorage.getItem("authToken");
        console.log("Token:", token); // Debug line


        const config = {
          headers: {
            //"Content-Type": "application/json",
           // 'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${token}`,
          },
        };


        let response = await gtaApi.submitApplication(user,config, formData);


        if (response.status === 401){
          await gtaApi.renewToken(); // Call your token renewal function here
          config.headers["Authorization"] = `Bearer ${localStorage.getItem("authToken")}`;

          response = await gtaApi.submitApplication(user,config, formData);

        }
        
        
        else if (response.status === 200) {
          Swal.fire({
            title: 'Success',
            text: 'Application submitted successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        } else {
          Swal.fire({
            title: 'Unexpected Response',
            text: 'The server returned an unexpected response. Please try again.',
            icon: 'warning',
            confirmButtonText: 'OK'
          });
        }
      } else {
        Swal.fire({
          title: 'Missed Information',
          text: form_error_descript,
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }

    } catch (error) {
      
      // Log error and display an error alert
      console.error('Submission error:', error);
      Swal.fire({
        title: 'Submission Failed',
        text: 'There was an error submitting the application. Please try again later.',
        icon: 'error',
        confirmButtonText: 'OK'
      });
    }
  };

  

  // Create the selected courses based on grades A or B
  useEffect(() => {
    const coursesToApply = studentHistory
      .filter(entry => entry.grade === 'A' || entry.grade === 'B')
      .map((entry, index) => ({ cyseId: entry.cyseId, order: index + 1 }));

    setSelectedCourses(coursesToApply);
  }, [studentHistory]);

  if (!isUser) {
    return <Navigate to='/' />;
  }

  return (
    <Container>
      <Form>

        {/* Segment for File Uploads */}
        <Segment>
          <h4>File Uploads</h4>
          <Form.Input
            type='file'
            label='Submit your Curriculum-Vitae (pdf)'
            onChange={(e) => handleFileChange(e, setCVFile)}
          />
          <Form.Input
            type='file'
            label='Please prepare a short video of you teaching a technical course or lab (irrespective of whether or not you are interested in teaching a recitation or lab). The video should be less than 3 minutes. Please convert the video into an mp4 file, preferably with codec H.264 mp4. We do not accept other video file formats. The application would also be incomplete if the interview committee could not play the video. Please double-check the video quality before you submit it. Video images should be clear and steady—no blurry, up-side-down videos, etc. If you have previously applied for an ECE GTA position and have already submitted your video, you do not need to submit the video again. We have a record of the video and can extract it from our files.'
            onChange={(e) => handleFileChange(e, setIntroGTAVideo)}
          />
        </Segment>

        <Segment>
          <h4>International Student Forms</h4>

          <Form.Field>
            <Form.Checkbox
              label='Are you an international student?'
              checked={isInternationalStudentCB}
              onChange={handleIntStdCheckboxChange}
            />
          </Form.Field>
          {isInternationalStudentCB && (
            <>
              <Form.Input
                type='file'
                label='Submit your Classroom English Language and Teaching Demonstration (CELTD) (pdf)'
                disabled={!isInternationalStudentCB} // Enable/disable based on checkbox
                onChange={(e) => handleFileChange(e, setCeltdCertFile)}
              />
              <Form.Input
                type='file'
                label='Submit your TOEFL Score (pdf)'
                disabled={!isInternationalStudentCB} // Enable/disable based on checkbox
                onChange={(e) => handleFileChange(e, setToeflScoreFile)}
              />
            </>
          )}
        </Segment>

        <Segment>
          <Form.Field>
            <Form.Checkbox
              label='Have you previously been a CYSE GTA?'
              checked={wasGTACB}
              onChange={handleGTAHistCheckboxChange}
            />
          </Form.Field>

          {wasGTACB && (
            <>
              <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>CYSE-ID</Table.HeaderCell>
                    <Table.HeaderCell>Semester</Table.HeaderCell>
                    <Table.HeaderCell>Year</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {gtaCourseHistory.map((entry, index) => (
                    <Table.Row key={index}>
                      <Table.Cell>
                        <Dropdown
                          selection
                          options={courseOptions}
                          value={entry.cyseId}
                          onChange={(e, { value }) => handleGtaHistoryChange(index, 'cyseId', value)}
                          placeholder='Select Course'
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Dropdown
                          selection
                          options={semesterOptions}
                          value={entry.semester}
                          onChange={(e, { value }) => handleGtaHistoryChange(index, 'semester', value)}
                          placeholder='Select Semester'
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Form.Input
                          value={entry.year}
                          onChange={(e) => handleGtaHistoryChange(index, 'year', e.target.value)}
                          placeholder='Enter Year'
                        />
                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>

              <Button onClick={addGtaHistoryRow} primary>Add Row</Button>
            </>
          )}
        </Segment>

        <Segment>
          <h4>CYSE Student History</h4>
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>CYSE ID</Table.HeaderCell>
                <Table.HeaderCell>SEMESTER</Table.HeaderCell>
                <Table.HeaderCell>YEAR</Table.HeaderCell>
                <Table.HeaderCell>GRADE</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              {studentHistory.map((entry, index) => (
                <Table.Row key={index}>
                  <Table.Cell>
                    <Dropdown
                      selection
                      options={courseOptions}
                      value={entry.cyseId}
                      onChange={(e, { value }) => handleStudentHistoryChange(index, 'cyseId', value)}
                      placeholder='Select Course'
                    />
                  </Table.Cell>
                  <Table.Cell>
                    <Dropdown
                      selection
                      options={semesterOptions}
                      value={entry.semester}
                      onChange={(e, { value }) => handleStudentHistoryChange(index, 'semester', value)}
                      placeholder='Select Semester'
                    />
                  </Table.Cell>
                  <Table.Cell>
                    <Form.Input
                      value={entry.year}
                      onChange={(e) => handleStudentHistoryChange(index, 'year', e.target.value)}
                      placeholder='Enter Year'
                    />
                  </Table.Cell>
                  <Table.Cell>
                    <Dropdown
                      selection
                      options={gradeOptions}
                      value={entry.grade}
                      onChange={(e, { value }) => handleStudentHistoryChange(index, 'grade', value)}
                      placeholder='Select Grade'
                    />
                  </Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table>

          <Button onClick={addStudentHistoryRow} primary>Add Row</Button>
        </Segment>

        {/* New Segment for Selected Courses */}
        <Segment>
          <h4>Select the courses that you want to apply</h4>
          <Table celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>CYSE ID</Table.HeaderCell>
                <Table.HeaderCell>Order</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {selectedCourses.map((course, index) => (
                <Table.Row key={index}>
                  <Table.Cell>{course.cyseId}</Table.Cell>
                  <Table.Cell>
                    <Form.Input
                      type='number'
                      value={course.order}
                      onChange={(e) => handleOrderChange(index, e.target.value)}
                      placeholder='Enter Order'
                    />
                  </Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table>
        </Segment>

        <Button onClick={handleSubmitApplication} primary>Submit Application</Button>
      </Form>
    </Container>
  );
}

export default UserPage
