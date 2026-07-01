async function publishAssignment(){

    const assignment={

        course_uid:getQuery("course_uid"),

        title:document.getElementById("title").value,

        content_type:"TEXT",

        content_data:document.getElementById("content").value

    };

    const result=await apiPost(

        API.ASSIGNMENT,

        assignment

    );

    alert(result.status);

}

async function loadAssignments(){

    const courseUid=getQuery("course_uid");

    const list=await apiGet(

        API.ASSIGNMENT+"/course/"+courseUid

    );

    render(list);

}

function render(assignments){

    const div=document.getElementById("assignmentList");

    div.innerHTML="";

    assignments.forEach(a=>{

        div.innerHTML+=`

            <p>

                ${a.title}

            </p>

        `;

    });

}